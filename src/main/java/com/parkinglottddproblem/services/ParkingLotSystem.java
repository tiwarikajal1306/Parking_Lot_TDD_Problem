package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.observer.ParkingLotObserver;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLotSystem {
    public List<ParkingLot> parkingLots;
    private final List<ParkingLotObserver> observers;
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

    public void parkVehicle(String vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.ALREADY_PARKED, "Vehicle already parked");

        if (isAnySlotAvailable()) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot Is Full");
        }
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, LocalTime.now().withNano(0));
        ParkingLot parkingLot = evenlyDistribution(this.parkingLots);
        int slotNumber = getEmptySlots(parkingLot);
        parkingLot.vehicles.set(slotNumber, parkingSlot);
       // parkingLot.vehicles.set(getEmptySlots(parkingLot), parkingSlot);
    }

    private ParkingLot evenlyDistribution(List<ParkingLot> parkingLot) {
        List<ParkingLot> parkingLotList = parkingLot;
        Collections.sort(parkingLotList, Comparator.comparing(ParkingLot::getVehicles));
        return parkingLotList.get(0);
    }
//
//    public LocalTime getParkTime(String vehicle) throws ParkingLotException {
//        for (ParkingSlot slot : vehicles) {
//            if (slot != null && slot.getVehicle().equals(vehicle)) {
//                return slot.getTime();
//            }
//        }
//        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, "vehicle not present");
//    }





    public LocalTime getParkTime(String vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : parkingLots) {
            for(ParkingSlot slot : parkingLot.vehicles)
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

    public boolean isAnySlotAvailable() {
        int vehicleCount = 0;
        for (ParkingLot parkingLot : parkingLots) {
            int vehicles = parkingLot.getVehicles();
            vehicleCount += vehicles;
            //vehicleCount = vehicleCount + vehicles;
        }
        return lotSize * numberOfLots == vehicleCount;
    }

    public boolean isVehicleParked(String vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            for(ParkingSlot slot : parkingLot.vehicles)
           if(slot != null && slot.getVehicle().equals(vehicle))
               return true;
        }
        return false;
        //return vehicles.stream().anyMatch(slot -> slot != null && slot.getVehicle() == vehicle);
    }

//    public boolean unPark(String vehicle) {
//        if (vehicle == null) return false;
//        for (ParkingSlot slot : vehicles) {
//            if (slot != null && slot.getVehicle().equals(vehicle)) {
//                this.vehicles.set(this.vehicles.indexOf(slot), null);
//                for (ParkingLotObserver observer : observers) {
//                    observer.capacityIsAvailable();
//                }
//                return true;
//            }
//        }
//        return false;
//    }



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




    public void initializeParkingLot() {
//        IntStream.range(0, this.numberOfLots).forEachOrdered(slots -> parkingLots.add(slots, new ParkingLot(this.lotSize)));
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
}