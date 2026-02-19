package com.chandu.OneToOne.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @SequenceGenerator(
            name = "address_seq",
            sequenceName = "address_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_seq"
    )
    private int addressId;

    private String city;
    private String state;
    private String pincode;

    public Address() {}

    public Address(String city, String state, String pincode) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    // getters & setters

    public int getAddressId() {
        return addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
