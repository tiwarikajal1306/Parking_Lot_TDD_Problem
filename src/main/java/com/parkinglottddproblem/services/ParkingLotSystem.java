package com.parkinglottddproblem.services;

import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.observer.ParkingLotObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final List<ParkingLotObserver> observers;
    public List<ParkingLot> parkingLots;
    int lotSize;
    int numberOfLots;

    public ParkingLotSystem(int capacity, int numberOfLots) {
        this.lotSize = capacity;
        this.observers = new ArrayList<>();
        parkingLots = new ArrayList<>();
        IntStream.range(0, numberOfLots).forEachOrdered(slots -> parkingLots.add(slots, new ParkingLot(capacity)));
        this.numberOfLots = numberOfLots;
    }

    public void register(ParkingLotObserver parkingObservers) {
        observers.add(parkingObservers);
    }

    public void parkVehicle(String vehicle, DriverType driverType) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.ALREADY_PARKED, "Vehicle already parked");

        if (isAnySlotFull()) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot Is Full");
        }
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, LocalTime.now().withNano(0));
        ParkingLot parkingLot = getParkingLotAccordingToDriverType(this.parkingLots, driverType);
        parkingLot.vehicles.set(getEmptySlots(parkingLot), parkingSlot);
    }

    private ParkingLot getParkingLotAccordingToDriverType(List<ParkingLot> parkingLot, DriverType driverType) {
        if (driverType == DriverType.HANDICAP_DRIVER)
            for (ParkingLot parkingLot2 : parkingLots) {
                for (ParkingSlot slot : parkingLot2.vehicles)
                    if (slot == null) {
                        return parkingLot2;
                    }
            }
        int minimumIndex = 0;
        int minimumSize = parkingLots.get(0).getVehicles();
        for (int slot = 0; slot < parkingLots.size(); slot++)
            if (parkingLots.get(slot).getVehicles() < minimumSize) {
                minimumIndex = slot;
                minimumSize = parkingLots.get(slot).getVehicles();
            }
        return parkingLots.get(minimumIndex);
    }


    public LocalTime getParkTime(String vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.vehicles)
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    return slot.getTime();
                }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, "vehicle not present");
    }

    public Integer getEmptySlots(ParkingLot parkingLot) {
        for (int i = 0; i < parkingLot.vehicles.size(); i++) {
            if (parkingLot.vehicles.get(i) == null)
                return i;
        }
        return null;
    }

    public boolean isAnySlotFull() {
        int vehicleCount = 0;
        for (ParkingLot parkingLot : parkingLots) {
            int vehicles = parkingLot.getVehicles();
            vehicleCount += vehicles;
        }
        return lotSize * numberOfLots == vehicleCount;
    }

    public boolean isVehicleParked(String vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.vehicles)
                if (slot != null && slot.getVehicle().equals(vehicle))
                    return true;
        }
        return false;
    }

    public boolean unPark(String vehicle) {
        if (vehicle == null) return false;
        for (ParkingLot parkingLot : parkingLots)
            for (ParkingSlot slot : parkingLot.vehicles) {
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    parkingLot.vehicles.set(parkingLot.vehicles.indexOf(slot), null);
                    for (ParkingLotObserver observer : observers) {
                        observer.capacityIsAvailable();
                    }
                    return true;
                }
            }
        return false;
    }

    public int findVehicleLocation(String vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : parkingLots)
            for (ParkingSlot slot : parkingLot.vehicles) {
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    return parkingLot.vehicles.indexOf(slot);
                }
            }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, " vehicle not found");
    }

    public String vehiclePosition(String vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            lot++;
            for (ParkingSlot slot : parkingLot.vehicles) {
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    int slot1 = parkingLot.vehicles.indexOf(slot);
                    return "Lot" +lot + " " + "Slot" +slot1;
                }
            }
        }
        return null;
    }
}