package com.archsystemsinc.pqrs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the measure_lookup database table.
 * 
 */
@Entity
@Table(name="measure_lookup")
@NamedQuery(name="MeasureLookup.findAll", query="SELECT m FROM MeasureLookup m")
public class MeasureLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	private String comment;		
	
	@Column(name="measure_id")
	private String measureId;

	@Column(name="measure_name")
	private String measureName;

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
	//@JsonManagedReference
	//@JsonIgnore
	//@OneToMany(fetch = FetchType.EAGER, mappedBy="measureLookup", cascade = CascadeType.ALL)
	//private List<MeasureWiseExclusionRate> measureWiseExclusionRates;

	public MeasureLookup() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

	public String getMeasureId() {
		return this.measureId;
	}

	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}

	public String getMeasureName() {
		return this.measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

//	public List<MeasureWiseExclusionRate> getMeasureWiseExclusionRates() {
//		return this.measureWiseExclusionRates;
//	}
//
//	public void setMeasureWiseExclusionRates(List<MeasureWiseExclusionRate> measureWiseExclusionRates) {
//		this.measureWiseExclusionRates = measureWiseExclusionRates;
//	}

//	public MeasureWiseExclusionRate addMeasureWiseExclusionRate(MeasureWiseExclusionRate measureWiseExclusionRate) {
//		getMeasureWiseExclusionRates().add(measureWiseExclusionRate);
//		measureWiseExclusionRate.setMeasureLookup(this);
//
//		return measureWiseExclusionRate;
//	}
//
//	public MeasureWiseExclusionRate removeMeasureWiseExclusionRate(MeasureWiseExclusionRate measureWiseExclusionRate) {
//		getMeasureWiseExclusionRates().remove(measureWiseExclusionRate);
//		measureWiseExclusionRate.setMeasureLookup(null);
//
//		return measureWiseExclusionRate;
//	}

}