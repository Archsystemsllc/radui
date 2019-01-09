package com.archsystemsinc.rad.model;

import java.util.Date;


/**
 * The persistent class for the score_card database table.
 * 
 */

public class MacAssignmentObject{
	
	
	private int id;
	
	
	private String macName;
	
	
	private String jurisdictionName;
	
	
	private String programName;
	
	
	private Integer defaultCalls;
	
	
	private String assignedCalls;
	
	
	private String assignedQualityMonitor;
	
	
	private String assignedMonthYear;
	
	
	private Date createdDate;
	
	
	private Date updatedDate;
	
	
	private String createdBy;
	
	
	private String updatedBy;
	
	
	private Date monthlySavedFinalDate;
	
	private String plannedCalls;
	
	private String createdMethod;
	
	private String assignedCallsForCindy;
	
	private String assignedCallsForLydia;
	
	private String assignedCallsForJaneene;
	
	
	private Integer macId;
	
	
	private Integer jurisdictionId;
	
	
	private Integer programId;
	
	private Integer macJurisdictionProgramCompleted;
	
	
	
	public MacAssignmentObject() {
	}

	



	public Integer getMacJurisdictionProgramCompleted() {
		return macJurisdictionProgramCompleted;
	}





	public void setMacJurisdictionProgramCompleted(Integer macJurisdictionProgramCompleted) {
		this.macJurisdictionProgramCompleted = macJurisdictionProgramCompleted;
	}





	public Integer getMacId() {
		return macId;
	}


	public void setMacId(Integer macId) {
		this.macId = macId;
	}





	public Integer getJurisdictionId() {
		return jurisdictionId;
	}





	public void setJurisdictionId(Integer jurisdictionId) {
		this.jurisdictionId = jurisdictionId;
	}





	public Integer getProgramId() {
		return programId;
	}





	public void setProgramId(Integer programId) {
		this.programId = programId;
	}





	public String getPlannedCalls() {
		return plannedCalls;
	}



	public void setPlannedCalls(String plannedCalls) {
		this.plannedCalls = plannedCalls;
	}



	public String getCreatedMethod() {
		return createdMethod;
	}



	public void setCreatedMethod(String createdMethod) {
		this.createdMethod = createdMethod;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public String getAssignedMonthYear() {
		return assignedMonthYear;
	}

	public void setAssignedMonthYear(String assignedMonthYear) {
		this.assignedMonthYear = assignedMonthYear;
	}
	

	public String getMacName() {
		return macName;
	}

	public void setMacName(String macName) {
		this.macName = macName;
	}

	public String getJurisdictionName() {
		return jurisdictionName;
	}

	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Integer getDefaultCalls() {
		return defaultCalls;
	}

	public void setDefaultCalls(Integer defaultCalls) {
		this.defaultCalls = defaultCalls;
	}

	

	public String getAssignedCalls() {
		return assignedCalls;
	}



	public void setAssignedCalls(String assignedCalls) {
		this.assignedCalls = assignedCalls;
	}



	public String getAssignedCallsForCindy() {
		return assignedCallsForCindy;
	}



	public void setAssignedCallsForCindy(String assignedCallsForCindy) {
		this.assignedCallsForCindy = assignedCallsForCindy;
	}



	public String getAssignedCallsForLydia() {
		return assignedCallsForLydia;
	}



	public void setAssignedCallsForLydia(String assignedCallsForLydia) {
		this.assignedCallsForLydia = assignedCallsForLydia;
	}



	public String getAssignedCallsForJaneene() {
		return assignedCallsForJaneene;
	}



	public void setAssignedCallsForJaneene(String assignedCallsForJaneene) {
		this.assignedCallsForJaneene = assignedCallsForJaneene;
	}



	public String getAssignedQualityMonitor() {
		return assignedQualityMonitor;
	}

	public void setAssignedQualityMonitor(String assignedQualityMonitor) {
		this.assignedQualityMonitor = assignedQualityMonitor;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getMonthlySavedFinalDate() {
		return monthlySavedFinalDate;
	}

	public void setMonthlySavedFinalDate(Date monthlySavedFinalDate) {
		this.monthlySavedFinalDate = monthlySavedFinalDate;
	}
	
	

}