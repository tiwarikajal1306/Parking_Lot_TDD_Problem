package com.parkinglottddproblem.services;

public class ParkingLotOwner {
    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
    public enum Flag {
        PARKING_IS_VACANT, PARKING_IS_FULL
    }
    Flag flag;
    public Flag getFlag() {
        this.setStatus();
        return flag;
    }
    public void setStatus() {
        boolean isFull = parkingLotSystem.isSlotFull();
        if (isFull)
            flag =  Flag.PARKING_IS_FULL;
        flag = Flag.PARKING_IS_VACANT;
    }
}
