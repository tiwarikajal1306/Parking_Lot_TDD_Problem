package parkinglottddproblem;

import com.parkinglottddproblem.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        parkingLotSystem.park("Car");
        boolean VehicleParked = parkingLotSystem.isVehicleParked();
        Assert.assertTrue(VehicleParked);
    }
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnFalse() {
        parkingLotSystem.park("Car");
        parkingLotSystem.unPark("Car");
        boolean VehicleUnParked = parkingLotSystem.isVehicleParked();
        Assert.assertFalse(VehicleUnParked);
    }
}
