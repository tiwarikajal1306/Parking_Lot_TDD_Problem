package parkinglottddproblem;

import com.parkinglottddproblem.enums.Car;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.observer.AirportSecurity;
import com.parkinglottddproblem.observer.ParkingLotOwner;
import com.parkinglottddproblem.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem(3, 1);
    }

    //UC1 Park the vehicle
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.parkVehicle("vehicle",DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            boolean isParked = parkingLotSystem.isVehicleParked("vehicle");
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    //UC2 unPark the vehicle
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            boolean isUnParked = parkingLotSystem.unPark("vehicle");
            Assert.assertTrue(isUnParked);
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            boolean isUnParked = parkingLotSystem.unPark("vehicle1");
            Assert.assertFalse(isUnParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC3
    @Test
    public void givenVehicleParked_WhenLotFull_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle4", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
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
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle4", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    //UC4
    @Test
    public void givenParkingLot_WhenNotFull_ShouldNotBeRedirectSecurityStaff() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.register(airportSecurity);
        try {
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle4", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark("vehicle3");
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    //UC5
    @Test
    public void givenWhenLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.register(parkingLotOwner);
        try {
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle4", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
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
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
        } catch (ParkingLotException e) {
            parkingLotSystem.unPark("vehicle2");
            boolean capacityFull = parkingLotOwner.isCapacityFull();
            Assert.assertFalse(capacityFull);
        }
    }

    //UC6
    @Test
    public void givenVehicle_ShouldPark_OnAvailableSlot() {
        try {
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenVehicle_WhenParkedShouldFindTheLocation() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            int vehicleLocation = parkingLotSystem.findVehicleLocation("vehicle");
            Assert.assertEquals(0, vehicleLocation);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
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
    public void givenVehicle_WhenParked_ShouldReturnTime() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            LocalTime parkTime = parkingLotSystem.getParkTime("vehicle");
            Assert.assertEquals(parkTime, LocalTime.now().withNano(0));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenNotParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle("vehicle", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            LocalTime parkTime = parkingLotSystem.getParkTime("vehicle2");
            Assert.assertEquals(parkTime, LocalTime.now().withNano(0));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NOT_FOUND, e.type);
        }
    }

    //UC9
    @Test
    public void givenVehicle_WhenParkedAndUnParkedInLot_ShouldEvenlyDistributed() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle4", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle5", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle6", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle7", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.unPark("vehicle7");
            String position = parkingLotSystem.vehiclePosition("vehicle3");
            Assert.assertEquals("Lot2 Slot0", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
    //UC10
    @Test
    public void givenVehicleToPark_WhenDriverIsHandicap_ShouldParkVehicleAtNearestSpot() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.unPark("vehicle2");
            parkingLotSystem.parkVehicle("vehicle4", DriverType.HANDICAP_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle5", DriverType.HANDICAP_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle6", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle7", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle8", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle9", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.unPark("vehicle7");
            parkingLotSystem.parkVehicle("vehicleH", DriverType.HANDICAP_DRIVER, Car.MEDIUM_CAR);
            String position = parkingLotSystem.vehiclePosition("vehicle4");
            Assert.assertEquals("Lot0 Slot1", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
    //UC11
    @Test
    public void givenVehicles_WhenVehicleShouldParkedAccordinglyType_ShouldReturnExpectedSlotNumbers() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle("vehicle1", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle2", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle3", DriverType.NORMAL_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle4", DriverType.HANDICAP_DRIVER, Car.MEDIUM_CAR);
            parkingLotSystem.parkVehicle("vehicle5", DriverType.HANDICAP_DRIVER, Car.LARGE_CAR);
            String position = parkingLotSystem.vehiclePosition("vehicle5");
            Assert.assertEquals("Lot1 Slot1", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}