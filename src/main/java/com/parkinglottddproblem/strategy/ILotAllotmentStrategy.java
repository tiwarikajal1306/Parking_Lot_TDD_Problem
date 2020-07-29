package com.parkinglottddproblem.strategy;

import com.parkinglottddproblem.services.ParkingLot;

import java.util.List;

public interface ILotAllotmentStrategy {
    ParkingLot getLotAllotment(List<ParkingLot> parkingLotList);
}
