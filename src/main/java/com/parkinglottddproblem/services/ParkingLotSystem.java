package com.parkinglottddproblem.services;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    public String vehicleName;
    public List<String> parkingLot = new ArrayList<>();

    public void park(String vehicle) {
        this.vehicleName = vehicle;
        parkingLot.add(vehicle);
    }

    public boolean isVehicleParked() {
        if (parkingLot.contains(vehicleName))
            return true;
        return false;
    }

    public void unPark(String vehicle) {
        if (parkingLot.contains(vehicle))
            parkingLot.remove(vehicle);
    }
}

