package com.analytics.backend.dto.auth;

public class RegisterRequest {

    private String username;
    private String password;

    // ✅ Getter
    public String getUsername() {
        return username;
    }

    // ✅ Setter
    public void setUsername(String username) {
        this.username = username;
    }

    // ✅ Getter
    public String getPassword() {
        return password;
    }

    // ✅ Setter
    public void setPassword(String password) {
        this.password = password;
    }
}