package com.parkinglottddproblem.exception;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
        PARKING_LOT_FULL, ALREADY_PARKED, NOT_FOUND;
    }
    public ExceptionType type;

    public ParkingLotException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
