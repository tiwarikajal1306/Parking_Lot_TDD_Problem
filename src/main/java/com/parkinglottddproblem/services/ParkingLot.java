package com.parkinglottddproblem.services;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final List<ParkingSlot> parkingSlotList;
    int lotSize;

    public ParkingLot(int lotSize) {
        this.lotSize = lotSize;
        parkingSlotList = new ArrayList<>();
        this.initializeParkingLot();
    }

    public void initializeParkingLot() {
        int bound = this.lotSize;
        for (int slots = 0; slots < bound; slots++) {
            ParkingSlot parkingSlot = null;
            parkingSlotList.add(parkingSlot);
        }
    }

    public int getParkingSlotList() {
        int numberOFVehicle = 0;
        for (int i = 0; i < lotSize; i++) {
            if (parkingSlotList.get(i) != null)
                numberOFVehicle++;
        }
        return numberOFVehicle;
    }

    public List<ParkingSlot> getList() {
        return parkingSlotList;
    }
}