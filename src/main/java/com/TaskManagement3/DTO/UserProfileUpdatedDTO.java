package com.TaskManagement3.DTO;

public class UserProfileUpdatedDTO {
	
	private Long id;
	private String userOfficialEmail;
	private String userName;
	private String deartment; 
	private String designation;
	private String organizationName;
	private String profileUpdated;
	private boolean active;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserOfficialEmail() {
		return userOfficialEmail;
	}
	public void setUserOfficialEmail(String userOfficialEmail) {
		this.userOfficialEmail = userOfficialEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeartment() {
		return deartment;
	}
	public void setDeartment(String deartment) {
		this.deartment = deartment;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getProfileUpdated() {
		return profileUpdated;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public void setProfileUpdated(String profileUpdated) {
		this.profileUpdated = profileUpdated;
	}
	
	

}
