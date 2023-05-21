package com.quintron.input;

import com.quintron.input.hid4java.Hid4JavaListener;
import org.hid4java.HidDevice;

import java.util.List;
//import com.quintron.input.jinput.JInput;

public class PTTDeviceTest {
    public static void main(String[] args) {
        PttDevice pttDevice = null;
        for (String arg : args) {
            if (arg.equals("-h")) pttDevice = new Hid4JavaListener();
//            else if (arg.equals("-j")) pttDevice = new JInput();
        }
        if (pttDevice == null)  pttDevice = new Hid4JavaListener();
//        Hid4JavaListener hid4JavaListener = new Hid4JavaListener();
        System.out.println("Using " + pttDevice.getListener());
        System.setProperty("java.library.path", ".");
//        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
        pttDevice.initPttDevices();
        pttDevice.start();
//        hid4JavaListener.printDevices();
        pttDevice.printDevices();
//        deviceList.fri
        List<String> deviceList = pttDevice.getDevices();
        for (int i = 0; i < deviceList.size(); i++) {
            System.out.println("i=" + i + deviceList.get(i));
        }
        while (true) {
            pttDevice.readInput();
            System.out.println("After readInput");
            // do nothing
        }
    }
}
