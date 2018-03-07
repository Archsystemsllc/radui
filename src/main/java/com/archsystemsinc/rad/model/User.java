package com.archsystemsinc.rad.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This persistent class for the user database table.
 */

public class User {
	private Long id;
	private String userName;
	private String password;
	
	private String passwordConfirm;
	private String passwordFromdb;
	
	
	private Role role;
	private OrganizationLookup organizationLookup;
	private String createdBy;
	private String updatedBy;
	private Date createdDate;
	private Date updateDate;
	private String emailId;
	
	private String firstName;
	private String middleName;
	private String lastName;
	
	private Long macId;

	private Long status;
	
	private Long pccId;
	

	
	private String jurId;
	
	private String[] jurisidictionId;
	private Long orgId;
	private Date lastLoggedinDate;
	
	
	//Temporary Variables
	
	private ArrayList<String> jurIdList;
	
	private String roleString;
	
	
	
	
	public String[] getJurisidictionId() {
		return jurisidictionId;
	}
	public void setJurisidictionId(String[] jurisidictionId) {
		this.jurisidictionId = jurisidictionId;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	public String getRoleString() {
		return roleString;
	}
	public void setRoleString(String roleString) {
		this.roleString = roleString;
	}
	public ArrayList<String> getJurIdList() {
		return jurIdList;
	}
	public void setJurIdList(ArrayList<String> jurIdList) {
		this.jurIdList = jurIdList;
	}
	public Date getLastLoggedinDate() {
		return lastLoggedinDate;
	}
	public void setLastLoggedinDate(Date lastLoggedinDate) {
		this.lastLoggedinDate = lastLoggedinDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getMacId() {
		return macId;
	}
	public void setMacId(Long macId) {
		this.macId = macId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public String getJurId() {
		return jurId;
	}
	public void setJurId(String jurId) {
		this.jurId = jurId;
	}
	public OrganizationLookup getOrganizationLookup() {
		return organizationLookup;
	}
	public void setOrganizationLookup(OrganizationLookup organizationLookup) {
		this.organizationLookup = organizationLookup;
	}
	public Long getPccId() {
		return pccId;
	}
	public void setPccId(Long pccId) {
		this.pccId = pccId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", passwordConfirm=");
		builder.append(passwordConfirm);
		builder.append(", role=");
		builder.append(role);
		builder.append(", organizationLookup=");
		builder.append(organizationLookup);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", middleName=");
		builder.append(middleName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", macId=");
		builder.append(macId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", pccId=");
		builder.append(pccId);
		builder.append(", jurId=");
		builder.append(jurId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append("]");
		return builder.toString();
	}
	public String getPasswordFromdb() {
		return passwordFromdb;
	}
	public void setPasswordFromdb(String passwordFromdb) {
		this.passwordFromdb = passwordFromdb;
	}
	
	
	    
}
