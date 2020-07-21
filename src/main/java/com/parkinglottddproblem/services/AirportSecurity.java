package com.parkinglottddproblem.services;

import static com.parkinglottddproblem.services.ParkingLotSystem.parkingLotList;

public class AirportSecurity {
    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    public boolean redirectSecurityStaff() {
        boolean isFull = parkingLotSystem.isLotFull(parkingLotList);
        return isFull == false;
    }
}