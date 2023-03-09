package com.quintron.input;

import java.util.List;

public interface PttDevice {
    void initPttDevices();
    String readInput();
    void start();
    void stop();
    List<String> getDevices();

    String getListener();
}
