package com.quintron.input;

import java.util.List;

public interface PttDeviceInterface {
    public PTTDevice initPttDevices();
    public String readInput();
    public void start(PTTDevice device);
    public void stop(PTTDevice device);
    public List<String> getDevices();

    public <T> T getListener();
}
