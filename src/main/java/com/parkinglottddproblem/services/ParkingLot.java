package com.parkinglottddproblem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
  int lotSize;
    public final List<ParkingSlot> vehicles;
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
        for (int i =0 ; i < lotSize; i++) {
            if (vehicles.get(i) != null)
                numberOFVehicle++;
        }
        return numberOFVehicle;
        }

//    public static int getNumberOfVehicles(ParkingLot parkingLot) {
//    }

}
