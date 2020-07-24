package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.observer.ParkingLotObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final int actualCapacity;
    private final List<ParkingSlot> vehicles;
    private final List<ParkingLotObserver> observers;

    public ParkingLotSystem(int capacity) {
        this.observers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.actualCapacity = capacity;
        this.initializeParkingLot();
    }

    public void register(ParkingLotObserver parkingObservers) {
        observers.add(parkingObservers);
    }

    public void parkVehicle(String vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.ALREADY_PARKED, "Vehicle already parked");

        if (this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot Is Full");
        }
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, LocalTime.now().withNano(0));
        this.vehicles.set(this.getEmptySlots(), parkingSlot);
    }

    public LocalTime getParkTime(String vehicle) throws ParkingLotException {
        for (ParkingSlot slot : vehicles) {
            if (slot != null && slot.getVehicle() == vehicle) {
                return slot.getTime();
            }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, "vehicle not present");
    }

    public Integer getEmptySlots() {
        for (int i = 0; i < this.actualCapacity; i++) {
            if (this.vehicles.get(i) == null)
                return i;
        }
        return null;
    }

    public boolean isVehicleParked(String vehicle) {
        return vehicles.stream().anyMatch(slot -> slot != null && slot.getVehicle() == vehicle);
    }

    public boolean unPark(String vehicle) {
        if (vehicle == null) return false;
        for (ParkingSlot slot : vehicles) {
            if (slot != null && slot.getVehicle() == vehicle) {
                this.vehicles.set(this.vehicles.indexOf(slot), null);
                for (ParkingLotObserver observer : observers) {
                    observer.capacityIsAvailable();
                }
                return true;
            }
        }
        return false;
    }

    public void initializeParkingLot() {
        IntStream.range(0, this.actualCapacity).forEach(slots -> vehicles.add(null));
    }

    public int findVehicleLocation(String vehicle) throws ParkingLotException {
        for (ParkingSlot slot : vehicles) {
            if (slot != null && slot.getVehicle() == vehicle) {
                return vehicles.indexOf(slot);
            }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, " vehicle not found");
    }
}