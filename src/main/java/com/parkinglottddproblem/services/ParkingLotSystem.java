package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.time.LocalTime;
public class ParkingLotSystem {
private final int actualCapacity;
private List<String> vehicles;
private final List<ParkingLotObserver> observers;
public LocalTime parkTime = null;
public LocalTime unParkTime = null;

    public ParkingLotSystem(int capacity) {
        this.observers = new ArrayList<>();
        this.vehicles = new ArrayList();
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public void register(ParkingLotObserver parkingObservers) {
        observers.add(parkingObservers);
    }

    public void parkVehicle(int slotNumber, String vehicle) throws ParkingLotException {
        if(isVehicleParked(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.ALREADY_PARKED, "Vehicle already parked");

        if(this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
            for (ParkingLotObserver observer: observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot Is Full");
        }
        this.vehicles.set(slotNumber, vehicle);
        parkTime = LocalTime.now();
    }

    public Integer getEmptySlots() {
        for (int i = 0; i < this.actualCapacity; i++) {
            if (this.vehicles.get(i) == null)
                return i;
        }
        return null;
    }

    public boolean isVehicleParked(String vehicle) {
        return this.vehicles.contains(vehicle);
    }

    public boolean unPark(String vehicle) {
        if (vehicle == null) return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
            unParkTime = LocalTime.now();
            for (ParkingLotObserver observer: observers) {
                observer.capacityIsAvailable();
            }
            return true;
        }
        return false;
    }

    public void initializeParkingLot() {
        IntStream.range(0, this.actualCapacity).forEach(slots -> vehicles.add(null));
    }

    public int findVehicleLocation(String vehicle) throws ParkingLotException {
        if (vehicles.contains(vehicle))
            return vehicles.indexOf(vehicle);
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, " vehicle not found");
    }
}