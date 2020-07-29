package com.parkinglottddproblem.strategy;

import com.parkinglottddproblem.services.ParkingLot;

import java.util.List;

public class NormalSmallCarAllotment implements ILotAllotmentStrategy {
    @Override
    public ParkingLot getLotAllotment(List<ParkingLot> parkingLotList) {
        int minimumIndex = 0;
        int minimumSize = parkingLotList.get(0).getParkingSlotList();
        for (int slot = 0; slot < parkingLotList.size(); slot++)
            if (parkingLotList.get(slot).getParkingSlotList() < minimumSize) {
                minimumIndex = slot;
                minimumSize = parkingLotList.get(slot).getParkingSlotList();
            }
        return parkingLotList.get(minimumIndex);
    }
}
