package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParkingLotSystem {

    int PARKING_LOT_SIZE = 3;
    public String vehicleName;
    public List<String> parkingLot = new LinkedList<>();

    public void park(String vehicle) throws ParkingLotException {
        if (parkingLot.size() >= PARKING_LOT_SIZE)
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "PARKING LOT IS FULL");
        this.vehicleName = vehicle;
        parkingLot.add(vehicle);
    }

    public void unPark(String vehicle) {
        if (parkingLot.contains(vehicle))
            parkingLot.remove(vehicle);
    }

    public boolean isVehicleParked() {
        if (parkingLot.contains(vehicleName))
            return true;
        return false;
    }

     public  boolean isSlotFull(){
         if (parkingLot.size() >= PARKING_LOT_SIZE)
             return true;
         return false;
     }
}

