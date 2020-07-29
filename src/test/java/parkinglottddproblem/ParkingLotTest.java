package parkinglottddproblem;

import com.parkinglottddproblem.enums.CarSize;
import com.parkinglottddproblem.enums.CarCompany;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.enums.VehicleColor;
import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.model.VehicleDetails;
import com.parkinglottddproblem.observer.AirportSecurity;
import com.parkinglottddproblem.observer.ParkingLotOwner;
import com.parkinglottddproblem.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            boolean isParked = parkingLotSystem.isVehicleParked("vehicle");
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicleParked_WhenAlreadyParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.ALREADY_PARKED, e.type);
        }
    }

    //UC2 unPark the vehicle
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            boolean isUnParked = parkingLotSystem.unPark("vehicle");
            Assert.assertTrue(isUnParked);
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenAnotherVehicle_WhenUnParked_ShouldReturnFalse() {
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenVehicle_WhenParkedShouldFindTheLocation() {
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            LocalTime parkTime = parkingLotSystem.getParkTime("vehicle");
            Assert.assertEquals(parkTime, LocalTime.now().withNano(0));
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenNotParked_ShouldThrowException() {
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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

            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle5", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle6", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle7", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
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

            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.unPark("vehicle2");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle5", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle6", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle7", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle8", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle9", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.unPark("vehicle7");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicleH", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            String position = parkingLotSystem.vehiclePosition("vehicle4");
            Assert.assertEquals("Lot0 Slot1", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC11
    @Test
    public void givenVehicleToPark_WhenDriverIsHandicap_AndCarIsLarge_ShouldReturnExpectedSlotNumbers() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle5", DriverType.HANDICAP_DRIVER,
                    CarSize.LARGE_CAR), "AA");
            String position = parkingLotSystem.vehiclePosition("vehicle5");
            Assert.assertEquals("Lot1 Slot1", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicleToPark_WhenDriverIsHandicap_AndCarIsMedium_ShouldReturnExpectedSlotNumbers() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle5", DriverType.HANDICAP_DRIVER,
                    CarSize.LARGE_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle6", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            String position = parkingLotSystem.vehiclePosition("vehicle6");
            Assert.assertEquals("Lot0 Slot2", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenVehicleToPark_WhenDriverIsNormal_AndCarIsLarge_ShouldReturnExpectedSlotNumbers() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle5", DriverType.NORMAL_DRIVER,
                    CarSize.LARGE_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle6", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            String position = parkingLotSystem.vehiclePosition("vehicle5");
            Assert.assertEquals("Lot1 Slot1", position);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC12
    @Test
    public void givenVehicleToPark_WhenHaveWhiteColor_ShouldReturnExpectedSlotNumbers() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle1", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR, VehicleColor.NO_COLOR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle2", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR, VehicleColor.WHITE), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle3", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR, VehicleColor.NO_COLOR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("vehicle4", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR, VehicleColor.WHITE), "AA");
            List<String> whiteColorVehicle = parkingLotSystem.getLocationOfWhiteVehicle(VehicleColor.WHITE);
            Assert.assertEquals(Arrays.asList("Lot0 Slot1", "Lot1 Slot0"), whiteColorVehicle);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC13
    @Test
    public void givenToyotaCar_WhenHaveBlueColor_ShouldReturnExpectedLocation() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8765", DriverType.NORMAL_DRIVER,
                    VehicleColor.NO_COLOR, CarCompany.TATA), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-68-KS-8776", DriverType.NORMAL_DRIVER,
                    VehicleColor.BLUE, CarCompany.TOYOTA), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8754", DriverType.NORMAL_DRIVER,
                    VehicleColor.WHITE, CarCompany.TATA), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8654", DriverType.NORMAL_DRIVER,
                    VehicleColor.BLUE, CarCompany.TATA), "AA");
            List<String> carDetail = parkingLotSystem.getLocationOfVehicleByGivingColorAndBrand(VehicleColor.BLUE,
                    CarCompany.TOYOTA);
            Assert.assertEquals(Arrays.asList("Lot1 Slot0 MH-68-KS-8776 AA"), carDetail);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC14
    @Test
    public void givenBMWCar_WhenParked_ShouldReturnTotalCountOFBMWCar() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8765", DriverType.NORMAL_DRIVER,
                    VehicleColor.NO_COLOR, CarCompany.TATA), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-68-KS-8776", DriverType.NORMAL_DRIVER,
                    VehicleColor.BLUE, CarCompany.BMW), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8754", DriverType.NORMAL_DRIVER,
                    VehicleColor.WHITE, CarCompany.BMW), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8654", DriverType.NORMAL_DRIVER,
                    VehicleColor.BLUE, CarCompany.TATA), "AA");
            int carCount = parkingLotSystem.getCountForOneBrandCar(CarCompany.BMW);
            Assert.assertEquals(2, carCount);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC15
    @Test
    public void givenVehiclesParked_WhenFindVehicleParkedInLast30Minutes_ShouldReturnVehicleSlotNumber() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8765", DriverType.NORMAL_DRIVER,
                    VehicleColor.NO_COLOR, CarCompany.TATA), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-68-KS-8776", DriverType.NORMAL_DRIVER,
                    VehicleColor.BLUE, CarCompany.BMW), "AA");
            List<String> vehicleDetail = parkingLotSystem.getVehicleDetailOfGivenTime(30);
            Assert.assertEquals(Arrays.asList("Lot0 Slot0", "Lot1 Slot0"), vehicleDetail);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC16
    @Test
    public void givenParkingLots_WhenFindVehiclesAccordinglySmallVehicleAndHandicapDriverType_ShouldReturnVehicleDetails() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8765", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-75-KS-7338", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            List<String> vehicleDetail = parkingLotSystem.
                    getVehicleDetailOfGivenDriverTypeAndCarSize(DriverType.HANDICAP_DRIVER, CarSize.SMALL_CAR, 0);
            Assert.assertEquals(Arrays.asList("Lot0 Slot0 MH-65-KS-8765 HANDICAP_DRIVER SMALL_CAR"), vehicleDetail);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }

    //UC17
    @Test
    public void givenParingLot_WhenHaveParkedCars_ShouldReturnTotalVehicle() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(3, 3);
        try {
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-8765", DriverType.HANDICAP_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-85-KS-7638", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-75-KS-7658", DriverType.HANDICAP_DRIVER,
                    CarSize.LARGE_CAR), "AA");
            parkingLotSystem.parkVehicle(new VehicleDetails("MH-65-KS-7438", DriverType.NORMAL_DRIVER,
                    CarSize.SMALL_CAR), "AA");
            int carCount = parkingLotSystem.carCount();
            Assert.assertEquals(4, carCount);
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
        }
    }
}