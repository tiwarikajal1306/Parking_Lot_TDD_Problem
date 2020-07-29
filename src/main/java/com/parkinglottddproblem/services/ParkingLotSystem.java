package com.parkinglottddproblem.services;

import com.parkinglottddproblem.enums.CarCompany;
import com.parkinglottddproblem.enums.CarSize;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.enums.VehicleColor;
import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.model.ParkingSlot;
import com.parkinglottddproblem.model.VehicleDetails;
import com.parkinglottddproblem.observer.ParkingLotObserver;
import com.parkinglottddproblem.strategy.AllotmentFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void parkVehicle(VehicleDetails vehicleDetails, String attendantName) throws ParkingLotException {
        if (isVehicleParked(vehicleDetails.getVehicle()))
            throw new ParkingLotException(ParkingLotException.ExceptionType.ALREADY_PARKED, "Vehicle already parked");

        if (isAnySlotFull()) {
            for (ParkingLotObserver observer : observers) {
                observer.capacityIsFull();
            }
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "Parking Lot Is Full");
        }
        ParkingSlot parkingSlot = new ParkingSlot(vehicleDetails, LocalTime.now().withNano(0), attendantName);
        ParkingLot parkingLot = new AllotmentFactory().lotAllotment(vehicleDetails).getLotAllotment(parkingLots);
        parkingLot.getList().set(getEmptySlots(parkingLot), parkingSlot);
    }

    public LocalTime getParkTime(String vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList())
                if (slot != null && slot.getVehicleDetails().getVehicle().equals(vehicle)) {
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
                if (slot != null && slot.getVehicleDetails().getVehicle().equals(vehicle))
                    return true;
        }
        return false;
    }

    public boolean unPark(String vehicle) {
        if (vehicle == null) return false;
        for (ParkingLot parkingLot : parkingLots)
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicleDetails().getVehicle().equals(vehicle)) {
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
            for (ParkingSlot slot : parkingLot.getList())
                if (slot != null && slot.getVehicleDetails().getVehicle().equals(vehicle))
                    return parkingLot.getList().indexOf(slot);
        throw new ParkingLotException(ParkingLotException.ExceptionType.NOT_FOUND, " vehicle not found");
    }

    public String vehiclePosition(String vehicle) {
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicleDetails().getVehicle().equals(vehicle)) {
                    int slot1 = parkingLot.getList().indexOf(slot);
                    return "Lot" + lot + " " + "Slot" + slot1;
                }
            }
            lot++;
        }
        return null;
    }

    public List<String> getLocationOfWhiteVehicle(VehicleColor vehicleColor) {
        List<String> whiteColorVehicleLocation = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicleDetails().getColor().equals(vehicleColor)) {
                    int slot1 = parkingLot.getList().indexOf(slot);
                    String location = "Lot" + lot + " " + "Slot" + slot1;
                    whiteColorVehicleLocation.add(location);
                }
            }
            lot++;
        }
        return whiteColorVehicleLocation;
    }

    public List<String> getLocationOfVehicleByGivingColorAndBrand(VehicleColor vehicleColor, CarCompany carCompany) {
        List<String> VehicleInformation = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicleDetails().getColor().equals(vehicleColor) &&
                        slot.getVehicleDetails().getCarCompany().equals(carCompany)) {
                    int slot1 = parkingLot.getList().indexOf(slot);
                    String information = "Lot" + lot + " " + "Slot" + slot1 + " " + slot.getVehicleDetails().getVehicle()
                            + " " + slot.getAttendantName();
                    VehicleInformation.add(information);
                }
            }
            lot++;
        }
        return VehicleInformation;
    }

    public int getCountForOneBrandCar(CarCompany carCompany) {
        return parkingLots.stream().mapToInt(parkingLot -> (int) parkingLot.getList().stream().
                filter(slot -> slot != null && slot.getVehicleDetails().
                        getCarCompany().equals(carCompany)).count()).sum();
    }

    public List<String> getVehicleDetailOfGivenTime(int minutes) {
        List<String> vehicleInformation = new ArrayList<>();
        int lot = 0;
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && Duration.between(slot.getTime(), LocalDateTime.now()).toMinutes() <= minutes) {
                    int slot1 = parkingLot.getList().indexOf(slot);
                    String location = "Lot" + lot + " " + "Slot" + slot1;
                    vehicleInformation.add(location);
                }
            }
            lot++;
        }
        return vehicleInformation;
    }

    public List<String> getVehicleDetailOfGivenDriverTypeAndCarSize(DriverType driverType, CarSize carSize, int lot) {
        List<String> vehicleInformation = new ArrayList<>();
        int lot1 = 0;
        for (ParkingLot parkingLot : parkingLots) {
            for (ParkingSlot slot : parkingLot.getList()) {
                if (slot != null && slot.getVehicleDetails().getDriverType().equals(driverType) &&
                        slot.getVehicleDetails().getVehicleSize().equals(carSize) && lot1 == lot) {
                    int slot1 = parkingLot.getList().indexOf(slot);
                    String location = "Lot" + lot + " " + "Slot" + slot1 + " " + slot.getVehicleDetails().getVehicle()
                            + " " + slot.getVehicleDetails().getDriverType() + " "
                            + slot.getVehicleDetails().getVehicleSize();
                    vehicleInformation.add(location);
                }
            }
            lot++;
        }
        return vehicleInformation;
    }

    public int carCount() {
        return parkingLots.stream().mapToInt(parkingLot -> (int) parkingLot.getList().stream().
                filter(Objects::nonNull).count()).sum();
    }
}