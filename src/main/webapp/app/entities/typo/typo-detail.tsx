import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './typo.reducer';
import { ITypo } from 'app/shared/model/typo.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITypoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TypoDetail = (props: ITypoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { typoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="typoReporterApp.typo.detail.title">Typo</Translate> [<b>{typoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="pageURL">
              <Translate contentKey="typoReporterApp.typo.pageURL">Page URL</Translate>
            </span>
          </dt>
          <dd>{typoEntity.pageURL}</dd>
          <dt>
            <span id="reporterName">
              <Translate contentKey="typoReporterApp.typo.reporterName">Reporter Name</Translate>
            </span>
          </dt>
          <dd>{typoEntity.reporterName}</dd>
          <dt>
            <span id="reporterComment">
              <Translate contentKey="typoReporterApp.typo.reporterComment">Reporter Comment</Translate>
            </span>
          </dt>
          <dd>{typoEntity.reporterComment}</dd>
          <dt>
            <span id="textBeforeTypo">
              <Translate contentKey="typoReporterApp.typo.textBeforeTypo">Text Before Typo</Translate>
            </span>
          </dt>
          <dd>{typoEntity.textBeforeTypo}</dd>
          <dt>
            <span id="textTypo">
              <Translate contentKey="typoReporterApp.typo.textTypo">Text Typo</Translate>
            </span>
          </dt>
          <dd>{typoEntity.textTypo}</dd>
          <dt>
            <span id="textAfterTypo">
              <Translate contentKey="typoReporterApp.typo.textAfterTypo">Text After Typo</Translate>
            </span>
          </dt>
          <dd>{typoEntity.textAfterTypo}</dd>
          <dt>
            <span id="correctionStatus">
              <Translate contentKey="typoReporterApp.typo.correctionStatus">Correction Status</Translate>
            </span>
          </dt>
          <dd>{typoEntity.correctionStatus}</dd>
        </dl>
        <Button tag={Link} to="/typo" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/typo/${typoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ typo }: IRootState) => ({
  typoEntity: typo.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypoDetail);
