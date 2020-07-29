package com.parkinglottddproblem.strategy;

import com.parkinglottddproblem.enums.CarSize;
import com.parkinglottddproblem.enums.DriverType;
import com.parkinglottddproblem.model.VehicleDetails;

public class AllotmentFactory {

    public ILotAllotmentStrategy lotAllotment(VehicleDetails vehicleDetails) {
        if (vehicleDetails.getVehicleSize() == CarSize.SMALL_CAR && vehicleDetails.getDriverType() == DriverType.HANDICAP_DRIVER)
            return new HandicapSmallCarAllotment();
        if (vehicleDetails.getVehicleSize() == CarSize.SMALL_CAR && vehicleDetails.getDriverType() == DriverType.NORMAL_DRIVER)
            return new NormalSmallCarAllotment();
        if (vehicleDetails.getVehicleSize() == CarSize.LARGE_CAR && vehicleDetails.getDriverType() == DriverType.HANDICAP_DRIVER)
            return new HandicapLargeCarAllotment();
        return new NormalLargeCarAllotment();
    }
}

