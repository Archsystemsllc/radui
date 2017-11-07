package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the sub_data_analysis database table.
 * 
 */
@Entity
@Table(name="sub_data_analysis")
@NamedQuery(name="SubDataAnalysis.findAll", query="SELECT s FROM SubDataAnalysis s")
public class SubDataAnalysis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;
	
	@Column(name="on_screen_help_text")
	private String onScreenHelpText;

	@Column(name="sub_data_analysis_description")
	private String subDataAnalysisDescription;

	@Column(name="sub_data_analysis_name")
	private String subDataAnalysisName;

	//bi-directional many-to-one association to ProviderHypothesi
	@OneToMany(mappedBy="subDataAnalysis")
	private List<ProviderHypothesis> providerHypothesis;

	//bi-directional many-to-one association to StatewiseStatistic
	@OneToMany(mappedBy="subDataAnalysis")
	private List<StatewiseStatistic> statewiseStatistics;
	
	//bi-directional many-to-one association to DataAnalysi
	@ManyToOne
	@JoinColumn(name="data_analysis_id")
	private DataAnalysis dataAnalysis;

	public SubDataAnalysis() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOnScreenHelpText() {
		return this.onScreenHelpText;
	}

	public void setOnScreenHelpText(String onScreenHelpText) {
		this.onScreenHelpText = onScreenHelpText;
	}
	
	public String getSubDataAnalysisDescription() {
		return this.subDataAnalysisDescription;
	}

	public void setSubDataAnalysisDescription(String subDataAnalysisDescription) {
		this.subDataAnalysisDescription = subDataAnalysisDescription;
	}

	public String getSubDataAnalysisName() {
		return this.subDataAnalysisName;
	}

	public void setSubDataAnalysisName(String subDataAnalysisName) {
		this.subDataAnalysisName = subDataAnalysisName;
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
		providerHypothesis.setSubDataAnalysis(this);

		return providerHypothesis;
	}

	public ProviderHypothesis removeProviderHypothesis(ProviderHypothesis providerHypothesis) {
		getProviderHypothesis().remove(providerHypothesis);
		providerHypothesis.setSubDataAnalysis(null);

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
		statewiseStatistic.setSubDataAnalysis(this);

		return statewiseStatistic;
	}

	public StatewiseStatistic removeStatewiseStatistic(StatewiseStatistic statewiseStatistic) {
		getStatewiseStatistics().remove(statewiseStatistic);
		statewiseStatistic.setSubDataAnalysis(null);

		return statewiseStatistic;
	}
	
	@JsonIgnore
	public DataAnalysis getDataAnalysis() {
		return this.dataAnalysis;
	}

	public void setDataAnalysis(DataAnalysis dataAnalysis) {
		this.dataAnalysis = dataAnalysis;
	}

}