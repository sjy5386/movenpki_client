package com.sysbot32.movenpki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileManager {
    public byte[] read(String filename) {
        return read(new File(filename));
    }

    public byte[] read(File parent, String child) {
        return read(new File(parent, child));
    }

    public byte[] read(File file) {
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
        write(new File(filename), data);
    }

    public void write(File parent, String child, byte[] data) {
        write(new File(parent, child), data);
    }

    public void write(File file, byte[] data) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
