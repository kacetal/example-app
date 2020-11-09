package com.kacetal.typoreporter.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.kacetal.typoreporter.domain.enumeration.TypoStatus;

/**
 * A Typo.
 */
@Entity
@Table(name = "typo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Typo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "page_url", nullable = false)
    private String pageURL;

    @NotNull
    @Column(name = "reporter_name", nullable = false)
    private String reporterName;

    @Column(name = "reporter_comment")
    private String reporterComment;

    @Column(name = "text_before_typo")
    private String textBeforeTypo;

    @NotNull
    @Column(name = "text_typo", nullable = false)
    private String textTypo;

    @Column(name = "text_after_typo")
    private String textAfterTypo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "correction_status", nullable = false)
    private TypoStatus correctionStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public Typo pageURL(String pageURL) {
        this.pageURL = pageURL;
        return this;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getReporterName() {
        return reporterName;
    }

    public Typo reporterName(String reporterName) {
        this.reporterName = reporterName;
        return this;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterComment() {
        return reporterComment;
    }

    public Typo reporterComment(String reporterComment) {
        this.reporterComment = reporterComment;
        return this;
    }

    public void setReporterComment(String reporterComment) {
        this.reporterComment = reporterComment;
    }

    public String getTextBeforeTypo() {
        return textBeforeTypo;
    }

    public Typo textBeforeTypo(String textBeforeTypo) {
        this.textBeforeTypo = textBeforeTypo;
        return this;
    }

    public void setTextBeforeTypo(String textBeforeTypo) {
        this.textBeforeTypo = textBeforeTypo;
    }

    public String getTextTypo() {
        return textTypo;
    }

    public Typo textTypo(String textTypo) {
        this.textTypo = textTypo;
        return this;
    }

    public void setTextTypo(String textTypo) {
        this.textTypo = textTypo;
    }

    public String getTextAfterTypo() {
        return textAfterTypo;
    }

    public Typo textAfterTypo(String textAfterTypo) {
        this.textAfterTypo = textAfterTypo;
        return this;
    }

    public void setTextAfterTypo(String textAfterTypo) {
        this.textAfterTypo = textAfterTypo;
    }

    public TypoStatus getCorrectionStatus() {
        return correctionStatus;
    }

    public Typo correctionStatus(TypoStatus correctionStatus) {
        this.correctionStatus = correctionStatus;
        return this;
    }

    public void setCorrectionStatus(TypoStatus correctionStatus) {
        this.correctionStatus = correctionStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Typo)) {
            return false;
        }
        return id != null && id.equals(((Typo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Typo{" +
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
