package com.example.demo.service;

import com.example.demo.entity.Vehicle;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {

    void parseAndSaveVehicles(MultipartFile file) throws Exception;

    void queueFileForBatchProcessing(MultipartFile file) throws Exception;

    Resource exportVehicles(Integer age);

    Resource exportVehiclesOlderThanFiveYears();

    Vehicle createVehicle(Vehicle vehicle);

    Vehicle updateVehicle(Long id, Vehicle vehicle);

    void deleteVehicle(Long id);

    Page<Vehicle> listVehicles(int page, int size);

    List<Vehicle> searchVehiclesByCarModel(String carModel);

    List<Vehicle> listAllVehicles();
}