package com.TaskManagement3.DTO;

public class LoginRequestDTO {

    private String userOfficialEmail;
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String userOfficialEmail, String password) {
        this.userOfficialEmail = userOfficialEmail;
        this.password = password;
    }

    public String getUserOfficialEmail() {
        return userOfficialEmail;
    }

    public void setUserOfficialEmail(String userOfficialEmail) {
        this.userOfficialEmail = userOfficialEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
