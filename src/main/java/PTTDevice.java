import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.jna.HidApi;

import hid4java.Hid4JavaListener;
import jinput.JinputListener;

public class PTTDevice implements PttDeviceInterface {

    @Override public PTTDevice initPttDevices() {
        return new PTTDevice();
    }

    @Override public void start(PTTDevice device) {

    }

    @Override public void stop(PTTDevice device) {

    }

    public static void main(String[] args) {
//        Hid4JavaListener hid4JavaListener = new Hid4JavaListener();
//        hid4JavaListener.start();
        System.setProperty("java.library.path", ".");
        JinputListener jinputListener = new JinputListener();
        jinputListener.init();
        while (true) {
            // do nothing
        }
    }
}
