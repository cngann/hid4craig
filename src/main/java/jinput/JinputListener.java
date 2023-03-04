package jinput;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class JinputListener {
    public void init() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("Thread rootPath is "+rootPath);
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllers.length == 0) {
            System.out.println("Found no controllers.");
            System.exit(0);
        }
    }

}
