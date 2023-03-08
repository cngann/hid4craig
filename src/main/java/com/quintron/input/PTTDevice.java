package com.quintron.input;

import com.quintron.input.jinput.JinputListener;

public class PTTDevice  {

    public PTTDevice initPttDevices() {
        return new PTTDevice();
    }

    public static void main(String[] args) {
//        Hid4JavaListener hid4JavaListener = new Hid4JavaListener();
//        hid4JavaListener.start();
        System.setProperty("java.library.path", ".");
//        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
//        System.setProperty("com.quintron.input.jinput.loglevel", "FINE");
        JinputListener jinputListener = new JinputListener();
        jinputListener.init();
        while (true) {
            // do nothing
        }
    }
}
