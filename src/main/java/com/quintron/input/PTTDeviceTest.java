package com.quintron.input;

import com.quintron.input.hid4java.Hid4JavaListener;
//import com.quintron.input.jinput.JInput;

public class PTTDeviceTest {
    public static void main(String[] args) {
        PttDevice pttDevice = null;
        for (String arg : args) {
            if (arg.equals("-h")) pttDevice = new Hid4JavaListener();
//            else if (arg.equals("-j")) pttDevice = new JInput();
        }
        if (pttDevice == null)  pttDevice = new Hid4JavaListener();
        System.out.println("Using " + pttDevice.getListener());
        System.setProperty("java.library.path", ".");
//        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
        pttDevice.initPttDevices();
        pttDevice.start();
        while (true) {
            pttDevice.readInput();
            System.out.println("After readInput");
            // do nothing
        }
    }
}
