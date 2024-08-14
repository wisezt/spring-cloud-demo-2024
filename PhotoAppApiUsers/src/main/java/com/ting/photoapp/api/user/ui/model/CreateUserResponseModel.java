package com.ting.photoapp.api.user.ui.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserResponseModel {

    @NotNull
    @Size(min=2, message ="First name must not be less than two characters")
    private String firstName;
    @NotNull
    @Size(min=2, message ="Last name must not be less than two characters")
    private String lastName;
    @NotNull
    private String userId;
    @NotNull
    @Email(message="Please provide valid email address")
    private String email;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
