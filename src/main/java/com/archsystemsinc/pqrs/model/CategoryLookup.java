package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the category_lookup database table.
 * 
 */
@Entity
@Table(name="category_lookup")
@NamedQuery(name="CategoryLookup.findAll", query="SELECT c FROM CategoryLookup c")
public class CategoryLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	private String name;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_date")
	private Date updatedDate;
	
	//bi-directional many-to-one association to MeasureWiseExclusionRate
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy="categoryLookup", cascade = CascadeType.ALL)
	private List<MeasureWiseExclusionRate> measureWiseExclusionRates;
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}	

	public CategoryLookup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MeasureWiseExclusionRate> getMeasureWiseExclusionRates() {
		return this.measureWiseExclusionRates;
	}

	public void setMeasureWiseExclusionRates(List<MeasureWiseExclusionRate> measureWiseExclusionRates) {
		this.measureWiseExclusionRates = measureWiseExclusionRates;
	}

	public MeasureWiseExclusionRate addMeasureWiseExclusionRate(MeasureWiseExclusionRate measureWiseExclusionRate) {
		getMeasureWiseExclusionRates().add(measureWiseExclusionRate);
		measureWiseExclusionRate.setCategoryLookup(this);

		return measureWiseExclusionRate;
	}

	public MeasureWiseExclusionRate removeMeasureWiseExclusionRate(MeasureWiseExclusionRate measureWiseExclusionRate) {
		getMeasureWiseExclusionRates().remove(measureWiseExclusionRate);
		measureWiseExclusionRate.setCategoryLookup(null);

		return measureWiseExclusionRate;
	}

}