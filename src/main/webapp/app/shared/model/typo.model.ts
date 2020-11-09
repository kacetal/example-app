import { TypoStatus } from 'app/shared/model/enumerations/typo-status.model';

export interface ITypo {
  id?: number;
  pageURL?: string;
  reporterName?: string;
  reporterComment?: string;
  textBeforeTypo?: string;
  textTypo?: string;
  textAfterTypo?: string;
  correctionStatus?: TypoStatus;
}

export const defaultValue: Readonly<ITypo> = {};
