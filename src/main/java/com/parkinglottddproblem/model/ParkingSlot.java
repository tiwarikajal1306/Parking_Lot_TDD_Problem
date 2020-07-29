package com.parkinglottddproblem.model;

import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlot {
    VehicleDetails vehicle;
    LocalTime time;
    String attendantName;

    public ParkingSlot(VehicleDetails vehicleDetails, LocalTime time, String attendantName) {
        this.vehicle = vehicleDetails;
        this.time = time;
        this.attendantName = attendantName;
    }

    public LocalTime getTime() {
        return time;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicle;
    }

    public String getAttendantName() {
        return attendantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(time, that.time);
    }
}
