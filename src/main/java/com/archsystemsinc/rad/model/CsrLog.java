/**
 * 
 */
package com.archsystemsinc.rad.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Abdul
 *
 */
public class CsrLog {
	
	private Long id;
	private Long userId;

	
	private Date createdDate;
	private Long complianceStatus;
	private Long uploadStatus;
	
	private Integer macId;
	private String jurisdiction;
	
	//Temporary Variables
	private ArrayList<String> jurisdictionNameList;	
	private ArrayList<Integer> macIdList;
	
	
	public ArrayList<String> getJurisdictionNameList() {
		return jurisdictionNameList;
	}
	public void setJurisdictionNameList(ArrayList<String> jurisdictionNameList) {
		this.jurisdictionNameList = jurisdictionNameList;
	}
	public ArrayList<Integer> getMacIdList() {
		return macIdList;
	}
	public void setMacIdList(ArrayList<Integer> macIdList) {
		this.macIdList = macIdList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getComplianceStatus() {
		return complianceStatus;
	}
	public void setComplianceStatus(Long complianceStatus) {
		this.complianceStatus = complianceStatus;
	}
	public Long getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(Long uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public Integer getMacId() {
		return macId;
	}
	public void setMacId(Integer macId) {
		this.macId = macId;
	}
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

}
