package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the data_analysis database table.
 * 
 */
@Entity
@Table(name="data_analysis")
@NamedQuery(name="DataAnalysis.findAll", query="SELECT d FROM DataAnalysis d")
public class DataAnalysis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="data_analysis_description")
	private String dataAnalysisDescription;

	@Column(name="data_analysis_name")
	private String dataAnalysisName;

	//bi-directional many-to-one association to ProviderHypothesis
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dataAnalysis", cascade = CascadeType.ALL)
	private List<ProviderHypothesis> providerHypothesis;

	//bi-directional many-to-one association to StatewiseStatistic
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dataAnalysis", cascade = CascadeType.ALL)
	private List<StatewiseStatistic> statewiseStatistics;
	
	//bi-directional many-to-one association to SubDataAnalysis
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dataAnalysis", cascade = CascadeType.ALL)
	private List<SubDataAnalysis> subDataAnalysis;

	public DataAnalysis() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataAnalysisDescription() {
		return this.dataAnalysisDescription;
	}

	public void setDataAnalysisDescription(String dataAnalysisDescription) {
		this.dataAnalysisDescription = dataAnalysisDescription;
	}

	public String getDataAnalysisName() {
		return this.dataAnalysisName;
	}

	public void setDataAnalysisName(String dataAnalysisName) {
		this.dataAnalysisName = dataAnalysisName;
	}
	@JsonIgnore
	public List<ProviderHypothesis> getProviderHypothesis() {
		return this.providerHypothesis;
	}

	public void setProviderHypothesis(List<ProviderHypothesis> providerHypothesis) {
		this.providerHypothesis = providerHypothesis;
	}

	public ProviderHypothesis addProviderHypothesis(ProviderHypothesis providerHypothesis) {
		getProviderHypothesis().add(providerHypothesis);
		providerHypothesis.setDataAnalysis(this);

		return providerHypothesis;
	}

	public ProviderHypothesis removeProviderHypothesis(ProviderHypothesis providerHypothesis) {
		getProviderHypothesis().remove(providerHypothesis);
		providerHypothesis.setDataAnalysis(null);

		return providerHypothesis;
	}
	
	@JsonIgnore
	public List<StatewiseStatistic> getStatewiseStatistics() {
		return this.statewiseStatistics;
	}

	public void setStatewiseStatistics(List<StatewiseStatistic> statewiseStatistics) {
		this.statewiseStatistics = statewiseStatistics;
	}

	public StatewiseStatistic addStatewiseStatistic(StatewiseStatistic statewiseStatistic) {
		getStatewiseStatistics().add(statewiseStatistic);
		statewiseStatistic.setDataAnalysis(this);

		return statewiseStatistic;
	}

	public StatewiseStatistic removeStatewiseStatistic(StatewiseStatistic statewiseStatistic) {
		getStatewiseStatistics().remove(statewiseStatistic);
		statewiseStatistic.setDataAnalysis(null);

		return statewiseStatistic;
	}
	
	@JsonIgnore
	public List<SubDataAnalysis> getSubDataAnalysis() {
		return this.subDataAnalysis;
	}

	public void setSubDataAnalysis(List<SubDataAnalysis> subDataAnalysis) {
		this.subDataAnalysis = subDataAnalysis;
	}

	public SubDataAnalysis addSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		getSubDataAnalysis().add(subDataAnalysis);
		subDataAnalysis.setDataAnalysis(this);

		return subDataAnalysis;
	}

	public SubDataAnalysis removeSubDataAnalysis(SubDataAnalysis subDataAnalysis) {
		getSubDataAnalysis().remove(subDataAnalysis);
		subDataAnalysis.setDataAnalysis(null);

		return subDataAnalysis;
	}

}