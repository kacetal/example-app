import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './typo.reducer';
import { ITypo } from 'app/shared/model/typo.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITypoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypoUpdate = (props: ITypoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { typoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/typo' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...typoEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="typoReporterApp.typo.home.createOrEditLabel">
            <Translate contentKey="typoReporterApp.typo.home.createOrEditLabel">Create or edit a Typo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : typoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="typo-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="typo-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="pageURLLabel" for="typo-pageURL">
                  <Translate contentKey="typoReporterApp.typo.pageURL">Page URL</Translate>
                </Label>
                <AvField
                  id="typo-pageURL"
                  type="text"
                  name="pageURL"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="reporterNameLabel" for="typo-reporterName">
                  <Translate contentKey="typoReporterApp.typo.reporterName">Reporter Name</Translate>
                </Label>
                <AvField
                  id="typo-reporterName"
                  type="text"
                  name="reporterName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="reporterCommentLabel" for="typo-reporterComment">
                  <Translate contentKey="typoReporterApp.typo.reporterComment">Reporter Comment</Translate>
                </Label>
                <AvField id="typo-reporterComment" type="text" name="reporterComment" />
              </AvGroup>
              <AvGroup>
                <Label id="textBeforeTypoLabel" for="typo-textBeforeTypo">
                  <Translate contentKey="typoReporterApp.typo.textBeforeTypo">Text Before Typo</Translate>
                </Label>
                <AvField id="typo-textBeforeTypo" type="text" name="textBeforeTypo" />
              </AvGroup>
              <AvGroup>
                <Label id="textTypoLabel" for="typo-textTypo">
                  <Translate contentKey="typoReporterApp.typo.textTypo">Text Typo</Translate>
                </Label>
                <AvField
                  id="typo-textTypo"
                  type="text"
                  name="textTypo"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="textAfterTypoLabel" for="typo-textAfterTypo">
                  <Translate contentKey="typoReporterApp.typo.textAfterTypo">Text After Typo</Translate>
                </Label>
                <AvField id="typo-textAfterTypo" type="text" name="textAfterTypo" />
              </AvGroup>
              <AvGroup>
                <Label id="correctionStatusLabel" for="typo-correctionStatus">
                  <Translate contentKey="typoReporterApp.typo.correctionStatus">Correction Status</Translate>
                </Label>
                <AvInput
                  id="typo-correctionStatus"
                  type="select"
                  className="form-control"
                  name="correctionStatus"
                  value={(!isNew && typoEntity.correctionStatus) || 'REPORTED'}
                >
                  <option value="REPORTED">{translate('typoReporterApp.TypoStatus.REPORTED')}</option>
                  <option value="IN_PROGRESS">{translate('typoReporterApp.TypoStatus.IN_PROGRESS')}</option>
                  <option value="RESOLVED">{translate('typoReporterApp.TypoStatus.RESOLVED')}</option>
                  <option value="CANCELED">{translate('typoReporterApp.TypoStatus.CANCELED')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/typo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  typoEntity: storeState.typo.entity,
  loading: storeState.typo.loading,
  updating: storeState.typo.updating,
  updateSuccess: storeState.typo.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypoUpdate);
