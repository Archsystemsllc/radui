/**
 * 
 */
package com.archsystemsinc.rad.model;

/**
 * @author 
 *
 */
public class UserFilter {

	private String lastName;
	
	private String macId;
	
	private String jurisId;
	
	private String roleIdString;
	
	private String orgIdString;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserFilter [lastName=");
		builder.append(lastName);
		builder.append(", roleId=");
		builder.append(roleIdString);
		builder.append(", orgId=");
		builder.append(orgIdString);
		builder.append(", macId=");
		builder.append(macId);
		builder.append(", jurisId=");
		builder.append(jurisId);
		builder.append("]");
		return builder.toString();
	}
	
	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public String getJurisId() {
		return jurisId;
	}

	public void setJurisId(String jurisId) {
		this.jurisId = jurisId;
	}


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRoleIdString() {
		return roleIdString;
	}

	public void setRoleIdString(String roleIdString) {
		this.roleIdString = roleIdString;
	}

	public String getOrgIdString() {
		return orgIdString;
	}

	public void setOrgIdString(String orgIdString) {
		this.orgIdString = orgIdString;
	}

	
	
}
