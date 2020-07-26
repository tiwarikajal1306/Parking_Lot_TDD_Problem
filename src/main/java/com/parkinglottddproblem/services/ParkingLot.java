package com.parkinglottddproblem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
        IntStream.range(0, bound).<ParkingSlot>mapToObj(slots -> null).forEachOrdered(vehicles::add);
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