package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;

import java.util.List;

public class ParkingAttendant {
    ParkingLotOwner owner = new ParkingLotOwner();
    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
    int parkingLotSize = 0;

    public ParkingAttendant(int parkingLotSize) {
        this.parkingLotSize = parkingLotSize;
    }

    public List<String> park(String vehicle, List<String> parkingLot) throws ParkingLotException {
        Integer emptyParkingSlot = owner.getEmptyParkingSlot(parkingLot);
        if (parkingLotSystem.isLotFull(parkingLot))
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "PARKING LOT IS FULL");
        parkingLot.add(emptyParkingSlot, vehicle);
        return parkingLot;
    }
}
