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
	
	private String roleId;
	
	private String orgId;

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserFilter [lastName=");
		builder.append(lastName);
		builder.append(", roleId=");
		builder.append(roleId);
		builder.append(", orgId=");
		builder.append(orgId);
		builder.append("]");
		return builder.toString();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
}
