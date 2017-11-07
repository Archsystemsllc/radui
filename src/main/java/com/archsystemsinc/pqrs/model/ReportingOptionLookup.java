package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the reporting_option_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
@Entity
@Table(name="reporting_option_lookup")
@NamedQuery(name="ReportingOptionLookup.findAll", query="SELECT r FROM ReportingOptionLookup r")
public class ReportingOptionLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="reporting_option_name")
	private String reportingOptionName;

	//bi-directional many-to-one association to Provider_Hypothesi
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "reportingOptionLookup", cascade = CascadeType.ALL)
	private List<ProviderHypothesis> providerHypothesis;

	public ReportingOptionLookup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReportingOptionName() {
		return this.reportingOptionName;
	}

	public void setReportingOptionName(String reportingOptionName) {
		this.reportingOptionName = reportingOptionName;
	}
	@JsonIgnore
	public List<ProviderHypothesis> getProviderHypothesis() {
		return this.providerHypothesis;
	}

	public void setProviderHypothesis(List<ProviderHypothesis> providerHypothesis) {
		this.providerHypothesis = providerHypothesis;
	}

	public ProviderHypothesis addProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().add(providerHypothesi);
		providerHypothesi.setReportingOptionLookup(this);

		return providerHypothesi;
	}

	public ProviderHypothesis removeProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().remove(providerHypothesi);
		providerHypothesi.setReportingOptionLookup(null);

		return providerHypothesi;
	}

}