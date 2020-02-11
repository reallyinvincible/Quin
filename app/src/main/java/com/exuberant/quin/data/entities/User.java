package com.exuberant.quin.data.entities;

public class User {

    private String name;
    private String email;
    private String hashedPassword;
    private String profilePicture;

    public User() {
    }

    public User(String name, String email, String hashedPassword, String profilePicture) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
