package com.archsystemsinc.rad.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This persistent class for the user database table.
 */

public class MacInfo {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    private String email;
    private Set<Role> roles = new HashSet<Role>();

    private List<Long> rolesList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
  
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Long> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<Long> rolesList) {
		this.rolesList = rolesList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
