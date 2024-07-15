package com.ebis.checkApp.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reports")
public class Reports implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name = "report_id", nullable = false)
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Tasks task;

    @ManyToOne
    @JoinColumn(name = "generated_by", nullable = false)
    private Users generatedBy;

    @Column(name = "report_url", nullable = false)
    private String reportUrl;

    @Column(name = "generated_at", nullable = false)
    private Timestamp generatedAt;

    // Getters and Setters

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public Users getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(Users generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }

    @Override
    public String toString() {
        return "Reports{" +
                "reportId=" + reportId +
                ", task=" + task +
                ", generatedBy=" + generatedBy +
                ", reportUrl='" + reportUrl + '\'' +
                ", generatedAt=" + generatedAt +
                '}';
    }
}
