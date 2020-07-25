package com.parkinglottddproblem.services;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    public final List<ParkingSlot> vehicles;
    int lotSize;

    public ParkingLot(int lotSize) {
        this.lotSize = lotSize;
        vehicles = new ArrayList<>();
        this.initializeParkingLot();
    }

    public void initializeParkingLot() {
        int bound = this.lotSize;
        for (int slots = 0; slots < bound; slots++) {
            vehicles.add(null);
        }
    }

    public int getVehicles() {
        int numberOFVehicle = 0;
        for (int i = 0; i < lotSize; i++) {
            if (vehicles.get(i) != null)
                numberOFVehicle++;
        }
        return numberOFVehicle;
    }
}
