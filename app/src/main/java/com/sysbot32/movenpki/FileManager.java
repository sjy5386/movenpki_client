package com.sysbot32.movenpki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileManager {
    public byte[] read(String filename) {
        File file = new File(filename);
        byte[] data;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }

    public void write(String filename, byte[] data) {
        File file = new File(filename);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
