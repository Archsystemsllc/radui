package com.archsystemsinc.rad.model;

public class MacProgJurisPccMapping  {

	private Integer id;

	private Integer macId;	

	private Integer programId;

	private Integer jurisdictionId;	

	private Integer pccId;
	
	private String macName;
	
	private String programName;
	
	private String jurisdictionName;
	
	private String pccLocationName;	
	

	public String getMacName() {
		return macName;
	}

	public void setMacName(String macName) {
		this.macName = macName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getJurisdictionName() {
		return jurisdictionName;
	}

	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
	}

	public String getPccLocationName() {
		return pccLocationName;
	}

	public void setPccLocationName(String pccLocationName) {
		this.pccLocationName = pccLocationName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMacId() {
		return macId;
	}

	public void setMacId(Integer macId) {
		this.macId = macId;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public Integer getJurisdictionId() {
		return jurisdictionId;
	}

	public void setJurisdictionId(Integer jurisdictionId) {
		this.jurisdictionId = jurisdictionId;
	}

	public Integer getPccId() {
		return pccId;
	}

	public void setPccId(Integer pccId) {
		this.pccId = pccId;
	}	
}
