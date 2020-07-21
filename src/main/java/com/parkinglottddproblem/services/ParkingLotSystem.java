package com.parkinglottddproblem.services;

import com.parkinglottddproblem.exception.ParkingLotException;
import java.util.LinkedList;
import java.util.List;

public class ParkingLotSystem {

    // int PARKING_LOT_SIZE = 3;
   public static List<String> parkingLotList = new LinkedList<>();
   ParkingAttendant parkingAttendant = new ParkingAttendant(3);

    public boolean park(String vehicle) throws ParkingLotException {
//        if (parkingLot.size() == PARKING_LOT_SIZE)
//            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_FULL, "PARKING LOT IS FULL");
        parkingLotList = parkingAttendant.park(vehicle, parkingLotList);
        parkingLotList.add(vehicle);
        return true;
    }

    public boolean unPark(String vehicle) {
        if (parkingLotList.contains(vehicle))
            return parkingLotList.remove(vehicle);
        return false;
    }

//     public boolean isLotFull(List<String> parkingLot) {
//         return this.parkingLotList.size() == PARKING_LOT_SIZE;
//     }


//    public static boolean isLotFull(List<String> parkingLot) {
//        if(parkingLot.size() == 0)
//            return false;
//        for (int i = 0; i < parkingLot.size(); i++) {
//            String s = parkingLot.get(i);
//            if (s == null)
//                return false;
//            // else return true;
//        }
//        return true;
//    }

    public boolean isLotFull(List<String> parkingLot) {
        if(parkingLot.size() < parkingAttendant.parkingLotSize)
            return false;
        return true;
    }
}