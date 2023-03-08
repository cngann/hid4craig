package com.quintron.input.jinput;

import de.ralleytn.plugins.jinput.xinput.XInputEnvironmentPlugin;
import net.java.games.input.*;

public class JinputListener {

    public void readInput(Controller[] controllers) {
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
    public void init() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("Thread rootPath is "+rootPath);
        ControllerEnvironment env = new XInputEnvironmentPlugin();
        if(!env.isSupported()) {
            env = ControllerEnvironment.getDefaultEnvironment();
        }
        Controller[] controllers = env.getControllers();
        if (controllers.length == 0) {
            System.out.println("Found no controllers.");
            System.exit(0);
        }
        System.out.println("Did we get here? " + controllers.length);
        readInput(controllers);
    }

}
