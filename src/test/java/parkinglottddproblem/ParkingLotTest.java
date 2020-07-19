package parkinglottddproblem;

import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.services.AirportSecurity;
import com.parkinglottddproblem.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        boolean carPark = parkingLotSystem.park("Car");
        Assert.assertTrue(carPark);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem.park("Car");
        parkingLotSystem.park("Car2");
        boolean carUnPark = parkingLotSystem.unPark("Car");
        Assert.assertTrue(carUnPark);
    }

    @Test
    public void givenManyVehicles_WhenParkingLotSizeIsFull_ShouldThrowException() throws ParkingLotException {
        try {
            parkingLotSystem.park("Tata Indigo CS");
            parkingLotSystem.park("Toyota Fortuner");
            parkingLotSystem.park("Maruti Swift Dzire");
            parkingLotSystem.park("Tata Hexa");
            parkingLotSystem.park("Maruti 800");
            parkingLotSystem.park("Suzuki Nexa");
        } catch (ParkingLotException e) {
            System.out.println(e.getMessage());
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParingLot_WhenFull_ShouldRedirect_SecurityStaff() throws ParkingLotException {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.park("Tata Indigo CS");
        parkingLotSystem.park("Toyota Fortuner");
        parkingLotSystem.park("Maruti Swift Dzire");
        parkingLotSystem.park("Tata Hexa");
        boolean result = airportSecurity.redirectSecurityStaff();
        Assert.assertTrue(result);
    }
}