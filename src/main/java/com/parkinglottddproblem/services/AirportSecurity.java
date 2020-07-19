package com.parkinglottddproblem.services;

public class AirportSecurity {
    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    public boolean redirectSecurityStaff() {
        boolean isFull = parkingLotSystem.isSlotFull();
        return isFull == false;
    }
}