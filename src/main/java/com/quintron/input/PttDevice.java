package com.quintron.input;

import org.hid4java.HidDevice;
import org.hid4java.HidServices;

import java.util.List;

public interface PttDevice {
    void initPttDevices();
    String readInput();
    void start();
    void stop();
    List<String> getDevices();
    void printDevices();

    String getListener();
}
