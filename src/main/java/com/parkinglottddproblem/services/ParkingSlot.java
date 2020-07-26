package com.parkinglottddproblem.services;

import com.parkinglottddproblem.model.VehicleDetails;

import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlot {
    VehicleDetails vehicle;
    LocalTime time;

    public ParkingSlot(VehicleDetails vehicleDetails, LocalTime time) {
        this.vehicle = vehicleDetails;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(time, that.time);
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "vehicle='" + vehicle + '\'' +
                ", time=" + time +
                '}';
    }
}
