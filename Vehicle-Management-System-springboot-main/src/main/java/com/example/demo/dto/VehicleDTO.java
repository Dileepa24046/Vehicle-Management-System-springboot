package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate; 
import java.io.Serializable;

@Getter
@Setter
public class VehicleDTO implements Serializable {

    private Long id;
    private String carMake;
    private String carModel;
    private String vin;
    private LocalDate manufacturedDate; 
    private Integer ageOfVehicle; 
    private Long customerId;  

}
