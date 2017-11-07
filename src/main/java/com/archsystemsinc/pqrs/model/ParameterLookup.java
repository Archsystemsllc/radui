package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the parameter_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
@Entity
@Table(name="parameter_lookup")
@NamedQuery(name="ParameterLookup.findAll", query="SELECT p FROM ParameterLookup p")
public class ParameterLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="parameter_name")
	private String parameterName;

	//bi-directional many-to-one association to Provider_Hypothesi
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parameterLookup", cascade = CascadeType.ALL)
	private List<ProviderHypothesis> providerHypothesis;

	public ParameterLookup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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
		providerHypothesi.setParameterLookup(this);

		return providerHypothesi;
	}

	public ProviderHypothesis removeProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().remove(providerHypothesi);
		providerHypothesi.setParameterLookup(null);

		return providerHypothesi;
	}

}