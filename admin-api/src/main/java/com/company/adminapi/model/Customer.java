package com.company.adminapi.model;

import javax.validation.constraints.*;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class Customer {
    @NotNull(message = "Enter customer id ")
    @Min(value = 1)
    private int id;
    @NotBlank(message = "Enter first name")
    @Size(max=50)
    private String firstName;
    @NotBlank(message = "Enter last name")
    @Size(max=50)
    private String lastName;
    @NotBlank(message = "Enter street address")
    @Size(max=50)
    private String street;
    @NotBlank(message = "Enter city")
    @Size(max=50)
    private String city;
    @NotBlank(message = "Enter zipcode ")
    @Size(min = 10, max=10)
    private String zip;
    @NotNull(message="Email Address required")
    @NotBlank(message="Email Address required")
    @Email(message = "Email Address not valid")
    @Size(max=75)
    private String email;
    @NotBlank(message = "Enter a phone number")
    @Size(max=20)
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(street, customer.street) &&
                Objects.equals(city, customer.city) &&
                Objects.equals(zip, customer.zip) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, street, city, zip, email, phone);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

