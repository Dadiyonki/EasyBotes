package com.example.ultimaoportunidad.models;

public class User {
    private String username;
    private String email;
    private String password;
    private byte[] userImage; // Para almacenar imágenes como BLOB

    // Constructor sin imagen
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Constructor con imagen
    public User(String username, String email, String password, byte[] userImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }
}
