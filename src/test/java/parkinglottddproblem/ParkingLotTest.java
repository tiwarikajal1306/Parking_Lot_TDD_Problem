package parkinglottddproblem;

import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.observer.AirportSecurity;
import com.parkinglottddproblem.observer.ParkingAttendant;
import com.parkinglottddproblem.observer.ParkingLotOwner;
import com.parkinglottddproblem.services.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {


    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(3);
    }

    //UC1 Park the vehicle
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(0, "vehicle", 2);
        boolean isParked = parkingLotSystem.isVehicleParked("vehicle");
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(0, "vehicle", 1);
            parkingLotSystem.parkVehicle(1, "vehicle", 2);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    //UC2 unPark the vehicle
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(0, "vehicle", 1);
        boolean isUnParked = parkingLotSystem.unPark("vehicle", 3);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem.parkVehicle(0, "vehicle", 1);
        boolean isUnParked = parkingLotSystem.unPark("vehicle1", 2);
        Assert.assertFalse(isUnParked);
    }

    //UC3
    @Test
    public void givenVehicleParked_WhenLotFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(0, "vehicle", 1);
            parkingLotSystem.parkVehicle(1, "vehicle2", 1);
            parkingLotSystem.parkVehicle(2, "vehicle3", 2);
            parkingLotSystem.parkVehicle(3, "vehicle4", 3);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    //UC4
    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurityStaff() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.register(airportSecurity);
        try {
            parkingLotSystem.parkVehicle(0, "vehicle", 1);
            parkingLotSystem.parkVehicle(1, "vehicle1", 1);
            parkingLotSystem.parkVehicle(2, "vehicle3", 2);
            parkingLotSystem.parkVehicle(3, "vehicle4", 2);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    //UC5
    @Test
    public void givenWhenLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.register(parkingLotOwner);
        try {
            parkingLotSystem.parkVehicle(0, "vehicle1", 1);
            parkingLotSystem.parkVehicle(1, "vehicle2", 2);
            parkingLotSystem.parkVehicle(2, "vehicle3", 2);
            parkingLotSystem.parkVehicle(3, "vehicle4", 1);
        } catch (ParkingLotException e) {
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenWhenLotIsEmpty_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            parkingLotSystem.register(parkingLotOwner);
            parkingLotSystem.parkVehicle(0, "vehicle", 1);
            parkingLotSystem.parkVehicle(1, "vehicle1", 2);
            parkingLotSystem.parkVehicle(2, "vehicle2", 1);
            parkingLotSystem.parkVehicle(3, "vehicle3", 2);
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark("vehicle2", 3);
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    //UC6
    @Test
    public void givenVehicle_ShouldPark_OnAvailableSlot() throws ParkingLotException {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        int slotNumber = parkingAttendant.parkingSlot(parkingLotSystem);
        parkingLotSystem.parkVehicle(slotNumber, "vehicle2", 1);
        int emptySlot2 = parkingAttendant.parkingSlot(parkingLotSystem);
        parkingLotSystem.parkVehicle(emptySlot2, "vehicle3", 1);
    }

    //UC7
    @Test
    public void givenVehicle_WhenParkedShouldFindTheLocation() throws ParkingLotException {
        parkingLotSystem.parkVehicle(0, "vehicle", 1);
        int vehicleLocation = parkingLotSystem.findVehicleLocation("vehicle");
        Assert.assertEquals(0, vehicleLocation);
    }

    @Test
    public void givenVehicle_WhenNotFound_ShouldThrowException() {
        try {
            parkingLotSystem.findVehicleLocation("vehicle2");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }

    //UC8
    @Test
    public void givenParkingTime_ParkingBillShouldBeGenerated() throws ParkingLotException {
        ParkingBill parkingBill = new ParkingBill();
        int emptySlot = parkingLotSystem.getEmptySlots();
        parkingLotSystem.parkVehicle(emptySlot, "vehicle", 1);
        parkingLotSystem.unPark("vehicle", 4);
        Assert.assertEquals(7.5, parkingBill.generateParkingBill(), 0);
    }

    // UC9
    @Test
    public void givenMultipleLots_WhenAdded_ShouldReturnTrue() {
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        ParkingLotSystem parkingLot1 = new ParkingLotSystem(3);
        parkingLotSystem.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingAttendant.addMultipleLots(parkingLotSystem);
        parkingAttendant.addMultipleLots(parkingLot1);
        boolean lotAdded = parkingAttendant.isLotAdded(parkingLot1);
        boolean lotAdded1 = parkingAttendant.isLotAdded(parkingLotSystem);
        Assert.assertTrue(lotAdded && lotAdded1);
    }
}