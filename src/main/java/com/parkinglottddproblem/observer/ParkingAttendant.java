package com.parkinglottddproblem.observer;

import com.parkinglottddproblem.services.ParkingLotSystem;

import java.util.ArrayList;
import java.util.List;

public class ParkingAttendant {


    public int parkingSlot(ParkingLotSystem parkingLot) {
        return new ParkingLotOwner().whereToPark(parkingLot);
    }


    List<ParkingLotSystem> parkingLotList;
    ParkingLotSystem parkingLot;

    public ParkingAttendant() {
        parkingLotList = new ArrayList<>();
        parkingLot = new ParkingLotSystem(3);
    }

    public void addMultipleLots(ParkingLotSystem parkingLot) {
        parkingLotList.add(parkingLot);
    }

    public boolean isLotAdded(ParkingLotSystem parkingLot) {
        if (this.parkingLotList.contains(parkingLot))
            return true;
        return false;
    }
}
