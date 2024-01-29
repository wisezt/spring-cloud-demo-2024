package com.ting.photoapp.api.users.data;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class UserEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 255)
    private String firstName;
    @Column(nullable = false, length = 255)
    private String lastName;
    @Column(nullable = true, length = 255, unique = true)
    private String email;
    @Column(nullable = false, length = 255)
    private String encryptedPassword;

    @Id
    @GeneratedValue
    private String Id;

    @Column(nullable = true, length = 255, unique = true)
    private String userId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String password) {
        this.encryptedPassword = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

}
