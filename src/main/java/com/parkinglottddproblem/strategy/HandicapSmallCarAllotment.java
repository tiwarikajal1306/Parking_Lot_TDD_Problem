package com.parkinglottddproblem.strategy;

import com.parkinglottddproblem.model.ParkingSlot;
import com.parkinglottddproblem.services.ParkingLot;

import java.util.List;

public class HandicapSmallCarAllotment implements ILotAllotmentStrategy {

    @Override
    public ParkingLot getLotAllotment(List<ParkingLot> parkingLotList) {
        //if (vehicleDetails.getDriverType() == DriverType.HANDICAP_DRIVER)
        for (ParkingLot parkingLot : parkingLotList) {
            for (ParkingSlot slot : parkingLot.getList())
                if (slot == null) {
                    return parkingLot;
                }
        }
        return null;
    }
}
