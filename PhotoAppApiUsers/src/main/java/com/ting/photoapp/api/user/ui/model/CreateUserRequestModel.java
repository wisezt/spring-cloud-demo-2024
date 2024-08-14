package com.ting.photoapp.api.user.ui.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {

    @NotNull
    @Size(min=2, message ="First name must not be less than two characters")
    private String firstName;
    @NotNull
    @Size(min=2, message ="Last name must not be less than two characters")
    private String lastName;
    @NotNull
    @Size(min=8, message ="Password must not be less than two characters")
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
