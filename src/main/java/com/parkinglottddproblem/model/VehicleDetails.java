package com.parkinglottddproblem.model;

import com.parkinglottddproblem.enums.Car;
import com.parkinglottddproblem.enums.CarCompany;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.enums.VehicleColor;

public class VehicleDetails {
    private final String vehicle;
    private final DriverType driverType;
    private Car car;
    private CarCompany carCompany;

    public VehicleColor getColor() {
        return color;
    }

    private VehicleColor color;

    public VehicleDetails(String vehicle,  DriverType driverType,  Car car) {
        this.vehicle = vehicle;
        this.driverType = driverType;
        this.car = car;
    }

    public VehicleDetails(String vehicle, DriverType driverType, Car car, VehicleColor color) {
        this.vehicle = vehicle;
        this.driverType = driverType;
        this.car = car;
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

    public Car getVehicleSize() {
        return car;
    }

    public CarCompany getCarCompany() {
        return carCompany;
    }

    public VehicleColor getCarColor() {
        return color;
    }
}
