package com.sysbot32.movenpki;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void zip(String input, String output) {
        String[] files = searchDirectory(input);
        try {
            makeDirectory(output);
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(output));
            for (String file : files) {
                System.out.println(file);
                zipOutputStream.putNextEntry(new ZipEntry(file.replace(input + "\\", "")));
                FileInputStream fileInputStream = new FileInputStream(file);
                int len = (int) Files.size(Paths.get(file));
                if (len > 0) {
                    byte[] buf = new byte[len];
                    fileInputStream.read(buf, 0, len);
                    zipOutputStream.write(buf, 0, len);
                }
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
            zipOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void unzip(String input, String output) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(input));
            ZipEntry zipEntry;
            while (Objects.nonNull(zipEntry = zipInputStream.getNextEntry())) {
                String path = output;
                String zipName = zipEntry.getName();
                System.out.println(zipName);
                makeDirectory(path, zipName);
                FileOutputStream fileOutputStream = new FileOutputStream(output + "\\" + zipName);
                byte[] buf = new byte[4096];
                int len;
                while ((len = zipInputStream.read(buf)) > 0) {
                    fileOutputStream.write(buf, 0, len);
                }
                zipInputStream.closeEntry();
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            zipInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] searchDirectory(String path) {
        ArrayList<String> files = new ArrayList<>();
        searchingDirectory(new File(path), files);
        return files.toArray(new String[0]);
    }

    private void searchingDirectory(File file, List<String> result) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                searchingDirectory(f, result);
            }
        } else {
            result.add(file.getPath());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void makeDirectory(String filename) throws Exception {
        String path = filename.substring(0, filename.lastIndexOf("\\"));
        Path p = Paths.get(path);
        if (Files.isDirectory(p)) {
            return;
        }
        Files.createDirectories(p);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void makeDirectory(String path, String zipName) throws Exception {
        int index = zipName.lastIndexOf("\\");
        if (index != -1) {
            path += "\\" + zipName.substring(0, index);
        }
        Path p = Paths.get(path);
        if (Files.isDirectory(p)) {
            return;
        }
        Files.createDirectories(p);
    }
}
