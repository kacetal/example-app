import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITypo, defaultValue } from 'app/shared/model/typo.model';

export const ACTION_TYPES = {
  FETCH_TYPO_LIST: 'typo/FETCH_TYPO_LIST',
  FETCH_TYPO: 'typo/FETCH_TYPO',
  CREATE_TYPO: 'typo/CREATE_TYPO',
  UPDATE_TYPO: 'typo/UPDATE_TYPO',
  DELETE_TYPO: 'typo/DELETE_TYPO',
  RESET: 'typo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITypo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TypoState = Readonly<typeof initialState>;

// Reducer

export default (state: TypoState = initialState, action): TypoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TYPO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TYPO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TYPO):
    case REQUEST(ACTION_TYPES.UPDATE_TYPO):
    case REQUEST(ACTION_TYPES.DELETE_TYPO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TYPO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TYPO):
    case FAILURE(ACTION_TYPES.CREATE_TYPO):
    case FAILURE(ACTION_TYPES.UPDATE_TYPO):
    case FAILURE(ACTION_TYPES.DELETE_TYPO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TYPO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TYPO):
    case SUCCESS(ACTION_TYPES.UPDATE_TYPO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TYPO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/typos';

// Actions

export const getEntities: ICrudGetAllAction<ITypo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TYPO_LIST,
    payload: axios.get<ITypo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITypo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TYPO,
    payload: axios.get<ITypo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITypo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TYPO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITypo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TYPO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITypo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TYPO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
