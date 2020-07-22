package com.parkinglottddproblem.observer;

import com.parkinglottddproblem.services.ParkingLotSystem;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public void capacityIsAvailable() {
        isFullCapacity = false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    public int whereToPark(ParkingLotSystem parkingLot) {
        return parkingLot.getEmptySlots();
    }
}
