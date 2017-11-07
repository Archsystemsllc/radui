package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the measure_wise_exclusion_rate database table.
 * 
 */
@Entity
@Table(name="measure_wise_exclusion_rate")
@NamedQuery(name="MeasureWiseExclusionRate.findAll", query="SELECT m FROM MeasureWiseExclusionRate m")
public class MeasureWiseExclusionRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private String createdDate;	

	@Column(name="exclusion_decisions")
	private String exclusionDecisions;

	private int frequencies;

	@Column(name="mean_exclusion_rate")
	private double meanExclusionRate;

	@Column(name="record_status")
	private int recordStatus;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="reporting_options")
	private String reportingOptions;

	public String getReportingOptions() {
		return reportingOptions;
	}

	public void setReportingOptions(String reportingOptions) {
		this.reportingOptions = reportingOptions;
	}

	//bi-directional many-to-one association to DataAnalysis
	@ManyToOne
	@JoinColumn(name="data_analysis_id")
	private DataAnalysis dataAnalysis;
		
	//bi-directional many-to-one association to SubDataAnalysis
	@ManyToOne
	@JoinColumn(name="sub_data_analysis_id")
	private SubDataAnalysis subDataAnalysis;	

	//bi-directional many-to-one association to YearLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="year_id")
	private YearLookup yearLookup;

	//bi-directional many-to-one association to CategoryLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="category_id")
	private CategoryLookup categoryLookup;

	//bi-directional many-to-one association to MeasureLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="measure_lookup_id")
	private MeasureLookup measureLookup;
	
	

	public MeasureWiseExclusionRate() {
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

	public String getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

//	public int getDataAnalysisId() {
//		return this.dataAnalysisId;
//	}
//
//	public void setDataAnalysisId(int dataAnalysisId) {
//		this.dataAnalysisId = dataAnalysisId;
//	}

	public String getExclusionDecisions() {
		return this.exclusionDecisions;
	}

	public void setExclusionDecisions(String exclusionDecisions) {
		this.exclusionDecisions = exclusionDecisions;
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

//	public int getSubDataAnalysisId() {
//		return this.subDataAnalysisId;
//	}
//
//	public void setSubDataAnalysisId(int subDataAnalysisId) {
//		this.subDataAnalysisId = subDataAnalysisId;
//	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

//	public int getYearId() {
//		return this.yearId;
//	}
//
//	public void setYearId(int yearId) {
//		this.yearId = yearId;
//	}

	@JsonIgnore
	public CategoryLookup getCategoryLookup() {
		return this.categoryLookup;
	}

	@JsonIgnore
	public DataAnalysis getDataAnalysis() {
		return dataAnalysis;
	}

	public void setDataAnalysis(DataAnalysis dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}

	@JsonIgnore
	public SubDataAnalysis getSubDataAnalysis() {
		return subDataAnalysis;
	}

	public void setSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		this.subDataAnalysis = subDataAnalysis;
	}

	@JsonIgnore
	public YearLookup getYearLookup() {
		return yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}

	public void setCategoryLookup(CategoryLookup categoryLookup) {
		this.categoryLookup = categoryLookup;
	}

	@JsonIgnore
	public MeasureLookup getMeasureLookup() {
		return this.measureLookup;
	}

	public void setMeasureLookup(MeasureLookup measureLookup) {
		this.measureLookup = measureLookup;
	}

}