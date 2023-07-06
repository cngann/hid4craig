package com.quintron.input.hid4java;

import com.quintron.input.PTTDeviceTest;
import com.quintron.input.PttDevice;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.HidServicesSpecification;
import org.hid4java.event.HidServicesEvent;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Hid4JavaListener implements HidServicesListener, PttDevice {
    public HidServices hidServices;
    List<String> friendlyDeviceNames = new ArrayList<>();

    public Hid4JavaListener() {
        initPttDevices();
    }

    @Override public void hidDeviceAttached(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        if (isPttDevice(hidDevice)) {
            hidDevice.open();
        }
        System.out.println("Device Attached: " + hidDevice.getManufacturer() + " " + hidDevice.getProduct());
        if (isPttDevice(hidDevice)) {
            if (PTTDeviceTest.existsPttIndexMapElement(hidDevice.getProduct()))
                return;
            PTTDeviceTest.addPttIndexMapElement(hidDevice.getProduct());
            int index = PTTDeviceTest.getPttIndexMapElement(hidDevice.getProduct());
            PTTDeviceTest.appendPttFile("" + index + ", " + hidDevice.getProduct());
        }
    }

    @Override public void hidDeviceDetached(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        if (isPttDevice(hidDevice)) {
            // Remove the element from the map
//            PTTDeviceTest.removePttIndexMapElement(hidDevice.getProduct());
            // Rebuild the file

            // remove the pttOn file if exists
            PTTDeviceTest.deletePttOnFile(hidDevice.getProduct());
        }
        hidDevice.close();

        System.out.println("Device Detached: " + hidDevice.getManufacturer() + " " + hidDevice.getProduct());
    }

    @Override public void hidFailure(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        System.out.println("Device Failed: " + hidDevice.getManufacturer() + " " + hidDevice.getProduct());
    }

    @Override
    public void start() {
        hidServices.start();
        System.out.println("Started");
    }

    public void printDevices() {
        List<HidDevice> hidDeviceList = hidServices.getAttachedHidDevices();
        System.out.println("PttDevices: " + hidDeviceList.size() + " devices");
        for (int i = 0; i < hidDeviceList.size(); i++) {
            HidDevice h = hidDeviceList.get(i);
            if (!isPttDevice(h))
                continue;
            String s = "" + i + ": mfg=[" + h.getManufacturer() + "], rel=[" + h.getReleaseNumber() +
                    "], usage=[" + h.getUsage() + "], page=[" + h.getUsagePage() + "], vid=[" + h.getVendorId() +
                    "], sn=[" + h.getSerialNumber() + "], product=[" + h.getProduct() + "]" +
                    ", index=" +  + PTTDeviceTest.getPttIndexMapElement(h.getProduct());
            System.out.println(s);
        }
    }

    public boolean isPttDevice(HidDevice device) {
        return (device.getUsagePage() == 0x1);
//        && (device.getUsage() == 0x4 || device.getUsage() == 0x4);
    }

    @Override public void hidDataReceived(HidServicesEvent hidServicesEvent) {
        HidDevice hidDevice = hidServicesEvent.getHidDevice();
        if (!isPttDevice(hidDevice)) {
            System.out.println("hidDataReceived: not a PTT device, product=" + hidDevice.getProduct());
            return;
        }
        byte[] bytes = hidServicesEvent.getDataReceived();
        int index = PTTDeviceTest.getPttIndexMapElement(hidDevice.getProduct());
        int i = 0;
        int pttOn = 0;
        int valueIndex = 0;
        if (bytes[0] == 9)
            valueIndex = 1;
        pttOn = bytes[valueIndex];
        if (pttOn == 1)
            PTTDeviceTest.createFile(hidDevice.getProduct());
        else
            PTTDeviceTest.deletePttOnFile(hidDevice.getProduct());
        System.out.print("index=" + index + ", pttOn=" + pttOn + ", [" + hidDevice.getProduct() + "] HID Data Rx: ");
        for (Byte aByte : bytes) {
            System.out.printf("%02x ", aByte);
            if (++i >= 2)
                break;
        }
        System.out.println();
    }

    @Override
    public void initPttDevices() {
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoStart(false);
        hidServicesSpecification.setAutoDataRead(true);
        hidServicesSpecification.setDataReadInterval(50);
        hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);
    }

    @Override
    public String readInput() {
        while (true) {
            // do nothing
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public List<String> getDevices() {
        return hidServices.getAttachedHidDevices().stream().map(HidDevice::getId).collect(Collectors.toList());
    }

    @Override
    public String getListener() {
        return this.getClass().getName();
    }
}
