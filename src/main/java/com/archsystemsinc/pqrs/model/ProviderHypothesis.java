package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the provider_hypothesis database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
@Entity
@Table(name="provider_hypothesis")
@NamedQuery(name="ProviderHypothesis.findAll", query="SELECT p FROM ProviderHypothesis p")
public class ProviderHypothesis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="no_count") 
	private BigInteger noCount;

	@Column(name="no_percent")
	private double noPercent;

	@Column(name="no_value")
	private int noValue;

	@Column(name="rp_percent")
	private double rpPercent;
	
	@Column(name="total_sum")
	private BigInteger totalSum;

	@Column(name="yes_count")
	private BigInteger yesCount;

	@Column(name="yes_percent")
	private double yesPercent;

	@Column(name="yes_value")
	private int yesValue;

	//bi-directional many-to-one association to ReportingTypeLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="reporting_option_id")
	private ReportingOptionLookup reportingOptionLookup;

	//bi-directional many-to-one association to YearLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="year_id")
	private YearLookup yearLookup;
	
	//bi-directional many-to-one association to ParameterLookup
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="parameter_id")
	private ParameterLookup parameterLookup;
	
	//bi-directional many-to-one association to DataAnalysis
	@ManyToOne
	@JoinColumn(name="data_analysis_id")
	private DataAnalysis dataAnalysis;
	
	//bi-directional many-to-one association to SubDataAnalysis
	@ManyToOne
	@JoinColumn(name="sub_data_analysis_id")
	private SubDataAnalysis subDataAnalysis;

	
	public ProviderHypothesis() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getNoCount() {
		return this.noCount;
	}

	public void setNoCount(BigInteger noCount) {
		this.noCount = noCount;
	}

	public double getNoPercent() {
		return this.noPercent;
	}

	public void setNoPercent(double noPercent) {
		this.noPercent = noPercent;
	}

	public int getNoValue() {
		return this.noValue;
	}

	public void setNoValue(int noValue) {
		this.noValue = noValue;
	}
	
	public double getRpPercent() {
		return rpPercent;
	}

	public void setRpPercent(double rpPercent) {
		this.rpPercent = rpPercent;
	}

	public BigInteger getTotalSum() {
		return this.totalSum;
	}

	public void setTotalSum(BigInteger totalSum) {
		this.totalSum = totalSum;
	}

	public BigInteger getYesCount() {
		return this.yesCount;
	}

	public void setYesCount(BigInteger yesCount) {
		this.yesCount = yesCount;
	}

	public double getYesPercent() {
		return this.yesPercent;
	}

	public void setYesPercent(double yesPercent) {
		this.yesPercent = yesPercent;
	}

	public int getYesValue() {
		return this.yesValue;
	}

	public void setYesValue(int yesValue) {
		this.yesValue = yesValue;
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
		return this.yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}
	@JsonIgnore
	public ParameterLookup getParameterLookup() {
		return parameterLookup;
	}

	public void setParameterLookup(ParameterLookup parameterLookup) {
		this.parameterLookup = parameterLookup;
	}

	/**
	 * @return the dataAnalysis
	 */
	@JsonIgnore
	public DataAnalysis getDataAnalysis() {
		return dataAnalysis;
	}

	/**
	 * @param dataAnalysis the dataAnalysis to set
	 */
	public void setDataAnalysis(DataAnalysis dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}

	/**
	 * @return the subDataAnalysis
	 */
	@JsonIgnore
	public SubDataAnalysis getSubDataAnalysis() {
		return subDataAnalysis;
	}

	/**
	 * @param subDataAnalysis the subDataAnalysis to set
	 */
	public void setSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		this.subDataAnalysis = subDataAnalysis;
	}

}