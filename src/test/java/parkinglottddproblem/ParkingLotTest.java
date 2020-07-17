package parkinglottddproblem;

import com.parkinglottddproblem.exception.ParkingLotException;
import com.parkinglottddproblem.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.park("Car");
        boolean VehicleParked = parkingLotSystem.isVehicleParked();
        Assert.assertTrue(VehicleParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem.park("Car");
        parkingLotSystem.unPark("Car");
        boolean VehicleUnParked = parkingLotSystem.isVehicleParked();
        Assert.assertFalse(VehicleUnParked);
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
}
