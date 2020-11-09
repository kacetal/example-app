import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Typo from './typo';
import TypoDetail from './typo-detail';
import TypoUpdate from './typo-update';
import TypoDeleteDialog from './typo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Typo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypoDeleteDialog} />
  </>
);

export default Routes;
