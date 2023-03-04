package hid4java;

import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.HidServicesSpecification;
import org.hid4java.event.HidServicesEvent;

public class Hid4JavaListener implements HidServicesListener {
    HidServices hidServices;

    public Hid4JavaListener() {
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoStart(false);
        hidServicesSpecification.setAutoDataRead(true);
        hidServicesSpecification.setDataReadInterval(100);
        hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);
        hidServices.start();
    }

    @Override public void hidDeviceAttached(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        if (isPttDevice(hidDevice)) {
            hidDevice.open();
        }
        System.out.println("Device Attached: " + hidDevice.getManufacturer() + " " + hidDevice.getProduct());
    }

    @Override public void hidDeviceDetached(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        hidDevice.close();
        System.out.println("Device Detached: " + hidDevice.getManufacturer() + " " + hidDevice.getProduct());
    }

    @Override public void hidFailure(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        System.out.println("Device Failed: " + hidDevice.getManufacturer() + " " + hidDevice.getProduct());
    }

    public void start() {
        System.out.println("Started");
    }

    public boolean isPttDevice(HidDevice device) {
        return device.getUsagePage() == 0x1 && device.getUsage() == 0x4;
    }

    @Override public void hidDataReceived(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        byte[] bytes = hidServicesEvent.getDataReceived();
        System.out.print(hidDevice.getProduct() + " ");
        for (Byte aByte : bytes) {
            System.out.printf("%02x ", aByte);
        }
        System.out.println("");
    }
}
