package parkinglottddproblem;

import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.services.AirportSecurity;
import com.parkinglottddproblem.services.ParkingLotOwner;
import com.parkinglottddproblem.services.ParkingLotSystem;
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
        parkingLotSystem.parkVehicle(0, "vehicle");
        boolean isParked = parkingLotSystem.isVehicleParked("vehicle");
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(0, "vehicle");
            parkingLotSystem.parkVehicle(1, "vehicle");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    //UC2 unPark the vehicle
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(0, "vehicle");
        boolean isUnParked = parkingLotSystem.unPark("vehicle");
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem.parkVehicle(0, "vehicle");
        boolean isUnParked = parkingLotSystem.unPark("vehicle1");
        Assert.assertFalse(isUnParked);
    }

    @Test
    public void givenVehicleParked_WhenLotFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(0, "vehicle");
            parkingLotSystem.parkVehicle(1, "vehicle2");
            parkingLotSystem.parkVehicle(2, "vehicle3");
            parkingLotSystem.parkVehicle(3, "vehicle4");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenWhenLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.register(parkingLotOwner);
        try {
            parkingLotSystem.parkVehicle(0, "vehicle1");
            parkingLotSystem.parkVehicle(1, "vehicle2");
            parkingLotSystem.parkVehicle(2, "vehicle3");
            parkingLotSystem.parkVehicle(3, "vehicle4");
        } catch (ParkingLotException e) {
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldRedirectSecurityStaff() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.register(airportSecurity);
        try {
            parkingLotSystem.parkVehicle(0, "vehicle");
            parkingLotSystem.parkVehicle(1, "vehicle1");
            parkingLotSystem.parkVehicle(2, "vehicle3");
            parkingLotSystem.parkVehicle(3, "vehicle4");
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenWhenLotIsEmpty_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        try {
            parkingLotSystem.register(parkingLotOwner);
            parkingLotSystem.parkVehicle(0, "vehicle");
            parkingLotSystem.parkVehicle(1, "vehicle1");
            parkingLotSystem.parkVehicle(2, "vehicle2");
            parkingLotSystem.parkVehicle(3, "vehicle3");
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark("vehicle2");
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    @Test
    public void demo() throws ParkingLotException {
        int emptySlot = parkingLotSystem.getEmptySlots();
        parkingLotSystem.parkVehicle(emptySlot, "vehicle2");
        int emptySlot2 = parkingLotSystem.getEmptySlots();
        parkingLotSystem.parkVehicle(emptySlot2, "vehicle3");
    }
}