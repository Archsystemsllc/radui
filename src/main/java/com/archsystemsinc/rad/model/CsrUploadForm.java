package com.archsystemsinc.rad.model;

/**
 * This persistent class for the user database table.
 */

public class CsrUploadForm {
    
    private String keepCurrentListCB;
    
    private String macIdK;
    private String macIdS;
    private String macIdU;
    
    private Integer userId;
    
    private String jurisdictionK;
    private String jurisdictionS;
    private String jurisdictionU;
    
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getKeepCurrentListCB() {
		return keepCurrentListCB;
	}
	public void setKeepCurrentListCB(String keepCurrentListCB) {
		this.keepCurrentListCB = keepCurrentListCB;
	}
	public String getMacIdK() {
		return macIdK;
	}
	public void setMacIdK(String macIdK) {
		this.macIdK = macIdK;
	}
	public String getMacIdS() {
		return macIdS;
	}
	public void setMacIdS(String macIdS) {
		this.macIdS = macIdS;
	}
	public String getMacIdU() {
		return macIdU;
	}
	public void setMacIdU(String macIdU) {
		this.macIdU = macIdU;
	}
	public String getJurisdictionK() {
		return jurisdictionK;
	}
	public void setJurisdictionK(String jurisdictionK) {
		this.jurisdictionK = jurisdictionK;
	}
	public String getJurisdictionS() {
		return jurisdictionS;
	}
	public void setJurisdictionS(String jurisdictionS) {
		this.jurisdictionS = jurisdictionS;
	}
	public String getJurisdictionU() {
		return jurisdictionU;
	}
	public void setJurisdictionU(String jurisdictionU) {
		this.jurisdictionU = jurisdictionU;
	} 
    
    
   
    

	
}