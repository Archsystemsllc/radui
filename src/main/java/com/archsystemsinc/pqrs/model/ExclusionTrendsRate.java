package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * This persistent class for the Exclusion_Trends_Rate database table.
 * 
 * @author venkat
 * @since 8/23/2017
 * 
 */
@Entity
@Table(name="exclusion_trends")
@NamedQuery(name="ExclusionTrendsRate.findAll", query="SELECT m FROM ExclusionTrendsRate m")
public class ExclusionTrendsRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private String createdDate;	

	@Column(name="updated_by")
	private String updatedBy;
	
	//bi-directional many-to-one association to ReportingTypeLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="reporting_option_id")
	private ReportingOptionLookup reportingOptionLookup;

	
	@Column(name="mean_exclusion_rate_percent")
	private double meanExclusionRatePercent;
	
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

	
	public ExclusionTrendsRate() {
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
	public ReportingOptionLookup getReportingOptionLookup() {
		return this.reportingOptionLookup;
	}

	public void setReportingOptionLookup(ReportingOptionLookup reportingOptionLookup) {
		this.reportingOptionLookup = reportingOptionLookup;
	}
	
	@JsonIgnore
	public YearLookup getYearLookup() {
		return yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}

	public double getMeanExclusionRatePercent() {
		return meanExclusionRatePercent;
	}

	public void setMeanExclusionRatePercent(double meanExclusionRatePercent) {
		this.meanExclusionRatePercent = meanExclusionRatePercent;
	}

}