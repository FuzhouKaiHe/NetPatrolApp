package com.khdz.patrol.netpatrolapp.Device.Usb;

/**
 * Created by fengscar on 2017/9/1.
 * 串口通信时的异常
 */

public class SerialException extends Exception {
    public enum SerialExceptionType {
        NoDevice,
        Timeout,
    }

    private SerialExceptionType mSerialExceptionType;

    public SerialExceptionType getSerialExceptionType() {
        return mSerialExceptionType;
    }

    public SerialException(String message) {
        super(message);
    }

    public SerialException(SerialExceptionType type) {
        mSerialExceptionType = type;
    }
}

