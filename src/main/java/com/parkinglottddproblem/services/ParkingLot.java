package com.parkinglottddproblem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    public final List<ParkingSlot> parkingSlotList;
    int lotSize;

    public ParkingLot(int lotSize) {
        this.lotSize = lotSize;
        parkingSlotList = new ArrayList<>();
        this.initializeParkingLot();
    }

    public void initializeParkingLot() {
        int bound = this.lotSize;
        IntStream.range(0, bound).<ParkingSlot>mapToObj(slots -> null).forEachOrdered(parkingSlotList::add);
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