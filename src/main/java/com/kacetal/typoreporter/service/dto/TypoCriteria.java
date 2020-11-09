package com.kacetal.typoreporter.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.kacetal.typoreporter.domain.enumeration.TypoStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.kacetal.typoreporter.domain.Typo} entity. This class is used
 * in {@link com.kacetal.typoreporter.web.rest.TypoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /typos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TypoStatus
     */
    public static class TypoStatusFilter extends Filter<TypoStatus> {

        public TypoStatusFilter() {
        }

        public TypoStatusFilter(TypoStatusFilter filter) {
            super(filter);
        }

        @Override
        public TypoStatusFilter copy() {
            return new TypoStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter pageURL;

    private StringFilter reporterName;

    private StringFilter reporterComment;

    private StringFilter textBeforeTypo;

    private StringFilter textTypo;

    private StringFilter textAfterTypo;

    private TypoStatusFilter correctionStatus;

    public TypoCriteria() {
    }

    public TypoCriteria(TypoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pageURL = other.pageURL == null ? null : other.pageURL.copy();
        this.reporterName = other.reporterName == null ? null : other.reporterName.copy();
        this.reporterComment = other.reporterComment == null ? null : other.reporterComment.copy();
        this.textBeforeTypo = other.textBeforeTypo == null ? null : other.textBeforeTypo.copy();
        this.textTypo = other.textTypo == null ? null : other.textTypo.copy();
        this.textAfterTypo = other.textAfterTypo == null ? null : other.textAfterTypo.copy();
        this.correctionStatus = other.correctionStatus == null ? null : other.correctionStatus.copy();
    }

    @Override
    public TypoCriteria copy() {
        return new TypoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPageURL() {
        return pageURL;
    }

    public void setPageURL(StringFilter pageURL) {
        this.pageURL = pageURL;
    }

    public StringFilter getReporterName() {
        return reporterName;
    }

    public void setReporterName(StringFilter reporterName) {
        this.reporterName = reporterName;
    }

    public StringFilter getReporterComment() {
        return reporterComment;
    }

    public void setReporterComment(StringFilter reporterComment) {
        this.reporterComment = reporterComment;
    }

    public StringFilter getTextBeforeTypo() {
        return textBeforeTypo;
    }

    public void setTextBeforeTypo(StringFilter textBeforeTypo) {
        this.textBeforeTypo = textBeforeTypo;
    }

    public StringFilter getTextTypo() {
        return textTypo;
    }

    public void setTextTypo(StringFilter textTypo) {
        this.textTypo = textTypo;
    }

    public StringFilter getTextAfterTypo() {
        return textAfterTypo;
    }

    public void setTextAfterTypo(StringFilter textAfterTypo) {
        this.textAfterTypo = textAfterTypo;
    }

    public TypoStatusFilter getCorrectionStatus() {
        return correctionStatus;
    }

    public void setCorrectionStatus(TypoStatusFilter correctionStatus) {
        this.correctionStatus = correctionStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypoCriteria that = (TypoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(pageURL, that.pageURL) &&
            Objects.equals(reporterName, that.reporterName) &&
            Objects.equals(reporterComment, that.reporterComment) &&
            Objects.equals(textBeforeTypo, that.textBeforeTypo) &&
            Objects.equals(textTypo, that.textTypo) &&
            Objects.equals(textAfterTypo, that.textAfterTypo) &&
            Objects.equals(correctionStatus, that.correctionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        pageURL,
        reporterName,
        reporterComment,
        textBeforeTypo,
        textTypo,
        textAfterTypo,
        correctionStatus
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (pageURL != null ? "pageURL=" + pageURL + ", " : "") +
                (reporterName != null ? "reporterName=" + reporterName + ", " : "") +
                (reporterComment != null ? "reporterComment=" + reporterComment + ", " : "") +
                (textBeforeTypo != null ? "textBeforeTypo=" + textBeforeTypo + ", " : "") +
                (textTypo != null ? "textTypo=" + textTypo + ", " : "") +
                (textAfterTypo != null ? "textAfterTypo=" + textAfterTypo + ", " : "") +
                (correctionStatus != null ? "correctionStatus=" + correctionStatus + ", " : "") +
            "}";
    }

}
