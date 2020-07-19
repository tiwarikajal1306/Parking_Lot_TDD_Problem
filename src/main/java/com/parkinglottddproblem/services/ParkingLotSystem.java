package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;
import java.util.LinkedList;
import java.util.List;

public class ParkingLotSystem {

    int PARKING_LOT_SIZE = 3;
    List<String> parkingLot = new LinkedList<>();

    public boolean park(String vehicle) throws ParkingLotException {
        if (parkingLot.size() == PARKING_LOT_SIZE)
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "PARKING LOT IS FULL");
        parkingLot.add(vehicle);
        return true;
    }

    public boolean unPark(String vehicle) {
        if (parkingLot.contains(vehicle))
           return parkingLot.remove(vehicle);
        return false;
    }

     public boolean isSlotFull() {
        int a = parkingLot.size();
         return this.parkingLot.size() == PARKING_LOT_SIZE;
     }
}