package com.parkinglottddproblem.services;

public class ParkingLotOwner {
    public enum Flag {
        PARKING_IS_VACANT, PARKING_IS_FULL
    }
    Flag flag;
    public Flag getFlag(ParkingLotSystem parkingLotSystem) {
        this.setStatus(parkingLotSystem);
        return flag;
    }
    private void setStatus(ParkingLotSystem parkingLotSystem) {
        boolean isFull = parkingLotSystem.isSlotFull();
        if (isFull)
            flag =  Flag.PARKING_IS_FULL;
        else flag = Flag.PARKING_IS_VACANT;
    }
}
