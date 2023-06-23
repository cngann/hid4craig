package com.quintron.input;

import com.quintron.input.hid4java.Hid4JavaListener;
import org.hid4java.HidDevice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import com.quintron.input.jinput.JInput;

public class PTTDeviceTest {
    public static Map<String, Integer> pttIndexMap = new HashMap<String, Integer>();
    public static void addPttIndexMapElement(String s) {
        pttIndexMap.put(s, pttIndexMap.size());
        System.out.println("PTTDeviceTest: added to map, [" + s + "], index=" + getPttIndexMapElement(s));
    }
    public static void removePttIndexMapElement(String s) {
        if (!pttIndexMap.containsKey(s))
            return;

        pttIndexMap.remove(s);
    }
    public static void buildFileFromMap() {
        // Delete the file
        deleteFile("pttList.txt");
        // Iterate through map to build the file
        Iterator<Map.Entry<String, Integer>> itr = pttIndexMap.entrySet().iterator();
        while(itr.hasNext())
        {
            Map.Entry<String, Integer> entry = itr.next();
            appendPttFile(entry.getKey());
        }
    }
    public static int getPttIndexMapElement(String s) {
        if (!pttIndexMap.containsKey(s))
            return -1;
        return pttIndexMap.get(s);
    }

    public static boolean existsPttIndexMapElement(String s) {
        if (pttIndexMap.containsKey(s))
            return true;
        return false;
    }

    public static void deleteFile(String fname) {
        String filename = productNameToFileName(fname);
        File pttFile = new File(filename);
        boolean deletedOk = pttFile.delete();
        if (deletedOk)
            System.out.println("deleteFile: delete OK, " + filename);
        else
            System.out.println("deleteFile: delete failure, " + filename);
    }

    public static void deletePttOnFile(String productName) {
        int index = PTTDeviceTest.getPttIndexMapElement(productName);
        String filename = index + ".pttOn";
        deleteFile(filename);
    }

    public static void appendPttFile(String s) {
        boolean successful = false;
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;
        try {
            File file = new File("pttList.txt");
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);
            pr.println(s);
        } catch (Exception e) {
            System.err.println("openPttFile: e=" + e);
        } finally {
            try {
                pr.close();
                br.close();
                fr.close();
            } catch (Exception e) {

            }
        }
    }

    public static void removeAllPttOnFiles() {
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (!listOfFiles[i].isFile())
                continue;
            String filename = listOfFiles[i].getName();
            if (!filename.endsWith(".pttOn"))
                continue;
            deleteFile(filename);
            System.out.println("removeAllPttOnFiles: deleted file=" + filename);
        }
    }

    public static void createFile(String productName) {
        try {
//            String filename = productNameToFileName(fname);
            int index = PTTDeviceTest.getPttIndexMapElement(productName);
            String filename = index + ".pttOn";
            File newFile = new File(filename);
            boolean success = newFile.createNewFile();
            System.out.println("createFile: created " + filename);
//            System.out.println("createFile: orig was " + fileNameToProductName(fname));
        } catch (Exception e) {
            System.err.println("createFile: e=" + e);
        }
    }

    public static String productNameToFileName(String productName) {
        String s = productName.replaceAll("/", "_slash_");
        s = s.replaceAll(":", "_colon_");
        return s;
    }

//    public static String fileNameToProductName(String filename) {
//        String s = filename.replaceAll("_slash_", "/");
//        s = s.replaceAll("_colon_", ":");
//    }

    public static void main(String[] args) {
        deleteFile("pttList.txt");
        removeAllPttOnFiles();
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
