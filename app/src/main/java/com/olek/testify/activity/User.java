package com.olek.testify.activity;


public class User {
    String displayName;
    String email;
    long createdAt;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public User() {
    }

    public User(String displayName, String email, long createdAt) {
        this.displayName = displayName;
        this.email = email;
        this.createdAt = createdAt;
    }
}
