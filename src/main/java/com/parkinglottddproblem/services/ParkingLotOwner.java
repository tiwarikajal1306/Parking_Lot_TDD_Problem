package com.parkinglottddproblem.services;

import java.util.List;

import static com.parkinglottddproblem.services.ParkingLotSystem.parkingLotList;
public class ParkingLotOwner {

    public enum Flag {
        PARKING_IS_VACANT, PARKING_IS_FULL
    }
    Flag flag;
    public Flag getFlag(ParkingLotSystem parkingLotSystem) {
        this.setStatus(parkingLotSystem);
        return flag;
    }
    private void setStatus(ParkingLotSystem parkingLotSystem) {
        boolean isFull = parkingLotSystem.isLotFull(parkingLotList);
        if (isFull)
            flag =  Flag.PARKING_IS_FULL;
        else flag = Flag.PARKING_IS_VACANT;
    }



//    public Integer getEmptyParkingSlot(List<String> parkingLot) {
//        if (parkingLot.size() == 0)
//            return 0;
//        for (int i = 0; i < parkingLot.size(); i++) {
//            if(parkingLot.get(i) == null)
//                return i;
//        }
//        return null;
//    }

    public Integer getEmptyParkingSlot(List<String> parkingLot) {
        for (int i = 0; i <= parkingLot.size(); i++) {
            if (parkingLot.get(i) == null)
                return i;
        }
        return null;
    }
}
