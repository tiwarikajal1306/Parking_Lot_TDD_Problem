package com.parkinglottddproblem.services;

import com.parkinglottddproblem.enums.Car;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.observer.ParkingLotObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    private final List<ParkingLotObserver> observers;
    private final List<ParkingLot> parkingLots;
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

    public void parkVehicle(String vehicle, DriverType driverType, Car car) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.ALREADY_PARKED, "Vehicle already parked");

        if (isAnySlotFull()) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot Is Full");
        }
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, LocalTime.now().withNano(0));
        ParkingLot parkingLot = getParkingLotAccordingToDriverTypeAndCarSize(driverType, car);
        parkingLot.getList().set(getEmptySlots(parkingLot), parkingSlot);
    }

    private ParkingLot getParkingLotAccordingToDriverTypeAndCarSize(DriverType driverType, Car car) {
        if (car == Car.LARGE_CAR)
            return this.getParkingLotAccordingToLargeCar(driverType);
        if (driverType == DriverType.HANDICAP_DRIVER)
            for (ParkingLot parkingLot : parkingLots) {
                for (ParkingSlot slot : parkingLot.getList())
                    if (slot == null) {
                        return parkingLot;
                    }
            }
        int minimumIndex = 0;
        int minimumSize = parkingLots.get(0).getParkingSlotList();
        for (int slot = 0; slot < parkingLots.size(); slot++)
            if (parkingLots.get(slot).getParkingSlotList() < minimumSize) {
                minimumIndex = slot;
                minimumSize = parkingLots.get(slot).getParkingSlotList();
            }
        return parkingLots.get(minimumIndex);
    }

    public ParkingLot getParkingLotAccordingToLargeCar(DriverType driverType) {
        if (driverType == DriverType.HANDICAP_DRIVER)
            for (ParkingLot parkingLot : parkingLots) {
                List<ParkingSlot> list = parkingLot.getList();
                for (int i = 0; i < list.size()-1; i++) {
                        ParkingSlot slot1 = list.get(i+1);
                        ParkingSlot slot = list.get(i);
                        if (slot == null && slot1 == null) {
                            return parkingLot;
                        }
                    }
            }
        int minimumIndex = 0;
        int minimumSize = parkingLots.get(0).getParkingSlotList();
        for (int slot = 0; slot < parkingLots.size(); slot++)
            if (parkingLots.get(slot).getParkingSlotList() < minimumSize) {
                minimumIndex = slot;
                minimumSize = parkingLots.get(slot).getParkingSlotList();
            }
        return parkingLots.get(minimumIndex);
    }

    public LocalTime getParkTime(String vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList())
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    return slot.getTime();
                }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, "vehicle not present");
    }

    public Integer getEmptySlots(ParkingLot parkingLot) {
        for (int i = 0; i < parkingLot.getList().size(); i++) {
            if (parkingLot.getList().get(i) == null)
                return i;
        }
        return null;
    }

    public boolean isAnySlotFull() {
        int vehicleCount = 0;
        for (ParkingLot parkingLot : parkingLots) {
            int vehicles = parkingLot.getParkingSlotList();
            vehicleCount += vehicles;
        }
        return lotSize * numberOfLots == vehicleCount;
    }

    public boolean isVehicleParked(String vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList())
                if (slot != null && slot.getVehicle().equals(vehicle))
                    return true;
        }
        return false;
    }

    public boolean unPark(String vehicle) {
        if (vehicle == null) return false;
        for (ParkingLot parkingLot : parkingLots)
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    parkingLot.getList().set(parkingLot.getList().indexOf(slot), null);
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
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    return parkingLot.getList().indexOf(slot);
                }
            }
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, " vehicle not found");
    }

    public String vehiclePosition(String vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicle().equals(vehicle)) {
                    int slot1 = parkingLot.getList().indexOf(slot);
                    return "Lot" +lot+ " " + "Slot" +slot1;
                }
            }
            lot++;
        }
        return null;
    }
}