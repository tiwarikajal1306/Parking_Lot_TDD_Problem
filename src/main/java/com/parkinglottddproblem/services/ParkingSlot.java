package com.parkinglottddproblem.services;

import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlot {
    String vehicle;
    LocalTime time;

    public ParkingSlot(String vehicle, LocalTime time) {
        this.vehicle = vehicle;
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getVehicle() {
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
}
