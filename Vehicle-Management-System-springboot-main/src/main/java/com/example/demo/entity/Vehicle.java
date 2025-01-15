package com.example.demo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String carmake;

    private String carmodel;

    private String vin;

    private LocalDate manufactureddate;

    private Integer ageofVehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id") 
    @JsonBackReference
    private Customer customer;

    public Integer getAge() {
        if (manufactureddate == null) {
            return null; 
        }
        return LocalDate.now().getYear() - manufactureddate.getYear();
    }

    public void setYear(int int1) {
        throw new UnsupportedOperationException("Unimplemented method 'setYear'");
    }
}    
