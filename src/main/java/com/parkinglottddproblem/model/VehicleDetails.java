package com.parkinglottddproblem.model;

import com.parkinglottddproblem.enums.CarSize;
import com.parkinglottddproblem.enums.CarCompany;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.enums.VehicleColor;

public class VehicleDetails {
    private final String vehicle;
    private final DriverType driverType;
    private CarSize carSize;
    private CarCompany carCompany;

    public VehicleColor getColor() {
        return color;
    }

    private VehicleColor color;

    public VehicleDetails(String vehicle,  DriverType driverType,  CarSize carSize) {
        this.vehicle = vehicle;
        this.driverType = driverType;
        this.carSize = carSize;
    }

    public VehicleDetails(String vehicle, DriverType driverType, CarSize carSize, VehicleColor color) {
        this.vehicle = vehicle;
        this.driverType = driverType;
        this.carSize = carSize;
        this.color = color;
    }

    public VehicleDetails(String vehicle, DriverType driverType, VehicleColor color, CarCompany carCompany) {
        this.vehicle = vehicle;
        this.driverType = driverType;
        this.color = color;
        this.carCompany = carCompany;
    }

    public String getVehicle() {
        return vehicle;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public CarSize getVehicleSize() {
        return carSize;
    }

    public CarCompany getCarCompany() {
        return carCompany;
    }
}
