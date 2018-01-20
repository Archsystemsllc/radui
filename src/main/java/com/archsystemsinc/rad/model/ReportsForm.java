package com.archsystemsinc.rad.model;

import java.util.Date;

/**
 * This persistent class for the user database table.
 */

public class ReportsForm {  
    
    private String macId;   
    
    private String userId;
    
    private String jurisId;
    
    private String programId;
    
    private String loc;
    
    private Date fromDate;
    
    private Date toDate;
    
    private String scoreCardType;

	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJurisId() {
		return jurisId;
	}

	public void setJurisId(String jurisId) {
		this.jurisId = jurisId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getScoreCardType() {
		return scoreCardType;
	}

	public void setScoreCardType(String scoreCardType) {
		this.scoreCardType = scoreCardType;
	}
}