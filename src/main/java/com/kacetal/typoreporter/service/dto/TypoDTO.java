package com.kacetal.typoreporter.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.kacetal.typoreporter.domain.enumeration.TypoStatus;

/**
 * A DTO for the {@link com.kacetal.typoreporter.domain.Typo} entity.
 */
public class TypoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String pageURL;

    @NotNull
    private String reporterName;

    private String reporterComment;

    private String textBeforeTypo;

    @NotNull
    private String textTypo;

    private String textAfterTypo;

    @NotNull
    private TypoStatus correctionStatus;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterComment() {
        return reporterComment;
    }

    public void setReporterComment(String reporterComment) {
        this.reporterComment = reporterComment;
    }

    public String getTextBeforeTypo() {
        return textBeforeTypo;
    }

    public void setTextBeforeTypo(String textBeforeTypo) {
        this.textBeforeTypo = textBeforeTypo;
    }

    public String getTextTypo() {
        return textTypo;
    }

    public void setTextTypo(String textTypo) {
        this.textTypo = textTypo;
    }

    public String getTextAfterTypo() {
        return textAfterTypo;
    }

    public void setTextAfterTypo(String textAfterTypo) {
        this.textAfterTypo = textAfterTypo;
    }

    public TypoStatus getCorrectionStatus() {
        return correctionStatus;
    }

    public void setCorrectionStatus(TypoStatus correctionStatus) {
        this.correctionStatus = correctionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypoDTO)) {
            return false;
        }

        return id != null && id.equals(((TypoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypoDTO{" +
            "id=" + getId() +
            ", pageURL='" + getPageURL() + "'" +
            ", reporterName='" + getReporterName() + "'" +
            ", reporterComment='" + getReporterComment() + "'" +
            ", textBeforeTypo='" + getTextBeforeTypo() + "'" +
            ", textTypo='" + getTextTypo() + "'" +
            ", textAfterTypo='" + getTextAfterTypo() + "'" +
            ", correctionStatus='" + getCorrectionStatus() + "'" +
            "}";
    }
}
