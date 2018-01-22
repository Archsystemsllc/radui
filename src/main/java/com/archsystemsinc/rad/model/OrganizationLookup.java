package com.archsystemsinc.rad.model;

import java.io.Serializable;


/**
 * The persistent class for the OrganizationLookup database table.
 * 
 */

public class OrganizationLookup implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer id;

	private String createdBy;


	private String createdDate;

	
	
	private String organizationName;

	
	private String updatedBy;

	
	private String updatedDate;

	public OrganizationLookup() {
	}

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
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


	public String getOrganizationName() {
		return organizationName;
	}


	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}