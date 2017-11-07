package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the statewise_statistics database table.
 * 
 * @author Grmahun Redda
 * @since 6/21/2017
 * 
 */
@Entity
@Table(name="statewise_statistics")
@NamedQuery(name="StatewiseStatistic.findAll", query="SELECT s FROM StatewiseStatistic s")
public class StatewiseStatistic implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	private BigInteger count;

	@Column(name="ep_or_gpro")
	private int epOrGpro;

	@Column(name="rural_urban")
	private int ruralUrban;

	private String state;

	@Column(name="yes_or_nooption")
	private int yesOrNooption;

	//bi-directional many-to-one association to DataAnalysi
	@ManyToOne
	@JoinColumn(name="data_analysis_id")
	private DataAnalysis dataAnalysis;

	//bi-directional many-to-one association to SubDataAnalysi
	@ManyToOne
	@JoinColumn(name="sub_data_analysis_id")
	private SubDataAnalysis subDataAnalysis;

	
	//bi-directional many-to-one association to ReportingOptionLookup
	@ManyToOne
	@JoinColumn(name="reporting_option_id")
	private ReportingOptionLookup reportingOptionLookup;

	//bi-directional many-to-one association to YearLookup
	@ManyToOne
	@JoinColumn(name="year_id")
	private YearLookup yearLookup;

	public StatewiseStatistic() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getCount() {
		return this.count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public int getEpOrGpro() {
		return this.epOrGpro;
	}

	public void setEpOrGpro(int epOrGpro) {
		this.epOrGpro = epOrGpro;
	}

	public int getRuralUrban() {
		return this.ruralUrban;
	}

	public void setRuralUrban(int ruralUrban) {
		this.ruralUrban = ruralUrban;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getYesOrNooption() {
		return this.yesOrNooption;
	}

	public void setYesOrNooption(int yesOrNooption) {
		this.yesOrNooption = yesOrNooption;
	}

	public DataAnalysis getDataAnalysis() {
		return this.dataAnalysis;
	}

	public void setDataAnalysis(DataAnalysis dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}

	public SubDataAnalysis getSubDataAnalysis() {
		return this.subDataAnalysis;
	}

	public void setSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		this.subDataAnalysis = subDataAnalysis;
	}
	
	public ReportingOptionLookup getReportingOptionLookup() {
		return this.reportingOptionLookup;
	}

	public void setReportingOptionLookup(ReportingOptionLookup reportingOptionLookup) {
		this.reportingOptionLookup = reportingOptionLookup;
	}

	public YearLookup getYearLookup() {
		return this.yearLookup;
	}

	public void setYearLookup(YearLookup yearLookup) {
		this.yearLookup = yearLookup;
	}

}