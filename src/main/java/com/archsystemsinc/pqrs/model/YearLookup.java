package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import java.util.Set;

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
 * The persistent class for the year_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
@Entity
@Table(name="year_lookup")
@NamedQuery(name="YearLookup.findAll", query="SELECT y FROM YearLookup y")
public class YearLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="year_name", length=45)
	private String yearName;

	//bi-directional many-to-one association to Provider_Hypothesi
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "yearLookup", cascade = CascadeType.ALL)
	private Set<ProviderHypothesis> providerHypothesis;

	public YearLookup() {
	}
	
	public YearLookup(String yearName) {
		this.yearName = yearName;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYearName() {
		return this.yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	
	public Set<ProviderHypothesis> getProviderHypothesis() {
		return this.providerHypothesis;
	}

	public void setProviderHypothesis(Set<ProviderHypothesis> providerHypothesis) {
		this.providerHypothesis = providerHypothesis;
	}

	public ProviderHypothesis addProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().add(providerHypothesi);
		providerHypothesi.setYearLookup(this);

		return providerHypothesi;
	}

	public ProviderHypothesis removeProviderHypothesi(ProviderHypothesis providerHypothesi) {
		getProviderHypothesis().remove(providerHypothesi);
		providerHypothesi.setYearLookup(null);

		return providerHypothesi;
	}

}