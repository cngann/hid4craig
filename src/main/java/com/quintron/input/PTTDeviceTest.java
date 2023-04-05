package com.quintron.input;

import com.quintron.input.hid4java.Hid4JavaListener;
import com.quintron.input.jinput.JInput;

public class PTTDeviceTest {
    public static void main(String[] args) {
        PttDevice pttDevice = null;
        for (String arg : args) {
            if (arg.equals("-h")) pttDevice = new Hid4JavaListener();
            else if (arg.equals("-j")) pttDevice = new JInput();
        }
        if (pttDevice == null)  pttDevice = new Hid4JavaListener();
        System.out.println("Using " + pttDevice.getListener());
//        Hid4JavaListener hid4JavaListener = new Hid4JavaListener();
//        hid4JavaListener.start();
        System.setProperty("java.library.path", ".");
//        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
//        System.setProperty("com.quintron.input.jinput.loglevel", "FINE");
//        JinputListener jinputListener = new JinputListener();
//        jinputListener.init();
        pttDevice.initPttDevices();
        while (true) {
            pttDevice.readInput();
            // do nothing
        }
    }
}
