package com.parkinglottddproblem.strategy;

import com.parkinglottddproblem.model.ParkingSlot;
import com.parkinglottddproblem.services.ParkingLot;

import java.util.List;

public class HandicapLargeCarAllotment implements ILotAllotmentStrategy {
    @Override
    public ParkingLot getLotAllotment(List<ParkingLot> parkingLotList) {
        for (ParkingLot parkingLot : parkingLotList) {
            List<ParkingSlot> list = parkingLot.getList();
            for (int i = 0; i < list.size() - 1; i++) {
                ParkingSlot slot1 = list.get(i + 1);
                ParkingSlot slot = list.get(i);
                if (slot == null && slot1 == null) {
                    return parkingLot;
                }
            }
        }
        return null;
    }
}
