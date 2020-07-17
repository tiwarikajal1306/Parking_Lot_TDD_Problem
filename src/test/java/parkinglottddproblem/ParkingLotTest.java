package parkinglottddproblem;

import com.parkinglottddproblem.services.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

ParkingLotSystem parkingLotSystem;

@Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
    parkingLotSystem.park("Car");
    boolean isVehicleParked = parkingLotSystem.isVehicleParked();
    Assert.assertTrue(isVehicleParked);
}
}
