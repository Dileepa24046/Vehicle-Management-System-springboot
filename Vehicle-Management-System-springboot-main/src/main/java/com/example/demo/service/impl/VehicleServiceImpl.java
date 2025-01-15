package com.example.demo.service.impl;

import com.example.demo.entity.Vehicle;
import com.example.demo.entity.Customer;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> listAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public void parseAndSaveVehicles(MultipartFile file) throws Exception {
       List<Vehicle> vehicles = new ArrayList<>();
try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
    String line;
    boolean isFirstLine = true;
    while ((line = reader.readLine()) != null) {
        if (isFirstLine) {
            isFirstLine = false;
            continue; // Skip the header line
        }
        String[] fields = line.split(",");
        Vehicle vehicle = new Vehicle();
        vehicle.setCarmake(fields[0]);
        vehicle.setCarmodel(fields[1]);
        vehicle.setVin(fields[2]);
        try {
            vehicle.setManufactureddate(LocalDate.parse(fields[3]));
        } catch (DateTimeParseException e) {
            // Skip this record if the date is invalid
            continue;
        }
        int age = Period.between(vehicle.getManufactureddate(), LocalDate.now()).getYears();
        vehicle.setAgeofVehicle(age);
        // Assuming customer ID is provided in the CSV
        Long customerId = Long.parseLong(fields[4]);
        Customer customer = new Customer();
        customer.setId(customerId);
        vehicle.setCustomer(customer);
        vehicles.add(vehicle);
    }
}
vehicleRepository.saveAll(vehicles);
}

    @Override
public Resource exportVehiclesOlderThanFiveYears() {
    List<Vehicle> oldVehicles = vehicleRepository.findAll().stream()
            .filter(vehicle -> vehicle.getAgeofVehicle() != null && vehicle.getAgeofVehicle() > 5)
            .collect(Collectors.toList());
// Continue with exporting logic, e.g., converting to CSV or Excel.

StringBuilder csvData = new StringBuilder();
csvData.append("ID,Car Make,Car Model,VIN,Manufactured Date,Age of Vehicle,Customer ID\n");
for (Vehicle vehicle : oldVehicles) {
            csvData.append(vehicle.getId()).append(",")
                .append(vehicle.getCarmake()).append(",")
                .append(vehicle.getCarmodel()).append(",")
                .append(vehicle.getVin()).append(",")
                .append(vehicle.getManufactureddate()).append(",")
                .append(vehicle.getAgeofVehicle()).append(",")
                .append(vehicle.getCustomer().getId()).append("\n");
        }

        ByteArrayResource resource = new ByteArrayResource(csvData.toString().getBytes());
        return resource;
    }

    @Override
    public void queueFileForBatchProcessing(MultipartFile file) throws Exception {
        // Implementation for queuing file for batch processing
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicle.getManufactureddate() != null) {
            int age = Period.between(vehicle.getManufactureddate(), LocalDate.now()).getYears();
            vehicle.setAgeofVehicle(age);
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));

        existingVehicle.setCarmake(updatedVehicle.getCarmake());
        existingVehicle.setCarmodel(updatedVehicle.getCarmodel());
        existingVehicle.setVin(updatedVehicle.getVin());
        existingVehicle.setManufactureddate(updatedVehicle.getManufactureddate());

        if (updatedVehicle.getManufactureddate() != null) {
            int age = Period.between(updatedVehicle.getManufactureddate(), LocalDate.now()).getYears();
            existingVehicle.setAgeofVehicle(age);
        }

        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        }
    }

    @Override
    public Page<Vehicle> listVehicles(int page, int size) {
        return vehicleRepository.findAll(PageRequest.of(page, size, Sort.by("manufactureddate").ascending()));
    }

    @Override
    public List<Vehicle> searchVehiclesByCarModel(String carModel) {
        return vehicleRepository.findByCarmodelContaining(carModel);
    }

    @Override
    public Resource exportVehicles(Integer age) {
        // Implementation for exporting vehicles to a CSV file
        return null;
    }
}