package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * This persistent class for the measure_wise_performance_and_reporting_rate database table.
 * 
 * @author venkat
 * @since 8/27/2017
 *
 */

@Entity
@Table(name="measure_wise_performance_and_reporting_rate")
@NamedQuery(name="MeasureWisePerformanceAndReportingRate.findAll", query="SELECT m FROM MeasureWisePerformanceAndReportingRate m")
public class MeasureWisePerformanceAndReportingRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;

	private int frequencies;

	@Column(name="mean_exclusion_rate")
	private double meanExclusionRate;

	@Column(name="record_status")
	private int recordStatus;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;

	//bi-directional many-to-one association to DataAnalysis
	@ManyToOne
	@JoinColumn(name="data_analysis_id")
	private DataAnalysis dataAnalysis;

	//bi-directional many-to-one association to MeasureLookup
	@ManyToOne
	@JoinColumn(name="measure_lookup_id")
	private MeasureLookup measureLookup;

	//bi-directional many-to-one association to ReportingOptionLookup
	@ManyToOne
	@JoinColumn(name="reporting_option_id")
	private ReportingOptionLookup reportingOptionLookup;

	//bi-directional many-to-one association to SubDataAnalysis
	@ManyToOne
	@JoinColumn(name="sub_data_analysis_id")
	private SubDataAnalysis subDataAnalysis;

	//bi-directional many-to-one association to YearLookup
	@ManyToOne
	@JoinColumn(name="year_id")
	private YearLookup yearLookup;

	public MeasureWisePerformanceAndReportingRate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getFrequencies() {
		return this.frequencies;
	}

	public void setFrequencies(int frequencies) {
		this.frequencies = frequencies;
	}

	public double getMeanExclusionRate() {
		return this.meanExclusionRate;
	}

	public void setMeanExclusionRate(double meanExclusionRate) {
		this.meanExclusionRate = meanExclusionRate;
	}

	public int getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@JsonIgnore
	public DataAnalysis getDataAnalysis() {
		return this.dataAnalysis;
	}

	public void setDataAnalysis(DataAnalysis dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}

	@JsonIgnore
	public MeasureLookup getMeasureLookup() {
		return this.measureLookup;
	}

	public void setMeasureLookup(MeasureLookup measureLookup) {
		this.measureLookup = measureLookup;
	}

	@JsonIgnore
	public ReportingOptionLookup getReportingOptionLookup() {
		return this.reportingOptionLookup;
	}

	public void setReportingOptionLookup(ReportingOptionLookup reportingOptionLookup) {
		this.reportingOptionLookup = reportingOptionLookup;
	}

	@JsonIgnore
	public SubDataAnalysis getSubDataAnalysis() {
		return this.subDataAnalysis;
	}

	public void setSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		this.subDataAnalysis = subDataAnalysis;
	}

	@JsonIgnore
	public YearLookup getYearLookup() {
		return this.yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}

}