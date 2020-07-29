package com.parkinglottddproblem.strategy;

import com.parkinglottddproblem.services.ParkingLot;

import java.util.List;

public class NormalLargeCarAllotment implements ILotAllotmentStrategy {
    @Override
    public ParkingLot getLotAllotment(List<ParkingLot> parkingLotList) {
        int minimumIndex = 0;
        int minimumSize = parkingLotList.get(0).getParkingSlotList();
        for (int parkingLot = 0; parkingLot < parkingLotList.size(); parkingLot++)
            if (parkingLotList.get(parkingLot).getParkingSlotList() < minimumSize) {
                minimumIndex = parkingLot;
                minimumSize = parkingLotList.get(parkingLot).getParkingSlotList();
            }
        return parkingLotList.get(minimumIndex);
    }
}
