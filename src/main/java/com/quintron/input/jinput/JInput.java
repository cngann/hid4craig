package com.quintron.input.jinput;

import com.quintron.input.PttDevice;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.ralleytn.plugins.jinput.xinput.XInputEnvironmentPlugin;

public class JInput implements PttDevice {
    public Controller[] controllers;

    @Override public void initPttDevices() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("Thread rootPath is " + rootPath);
        ControllerEnvironment env = new XInputEnvironmentPlugin();
        if (!env.isSupported()) {
            env = ControllerEnvironment.getDefaultEnvironment();
        }
        controllers = env.getControllers();
        if (controllers.length == 0) {
            System.out.println("Found no controllers.");
            System.exit(0);
        }
        System.out.println("Did we get here? " + controllers.length);
        readInput();
    }

    @Override public String readInput() {
        while (true) {
            for (Controller controller : controllers) {
                controller.poll();
                EventQueue queue = controller.getEventQueue();
                Event event = new Event();
                while (queue.getNextEvent(event)) {
                    Component comp = event.getComponent();
                    System.out.println(event.getValue());
                }
            }
        }
    }

    @Override public void start() {

    }

    @Override public void stop() {

    }

    @Override public List<String> getDevices() {
        return Arrays.stream(controllers).map(Controller::getName).collect(Collectors.toList());
    }

    @Override public String getListener() {
        return this.getClass().getName();
    }
}
