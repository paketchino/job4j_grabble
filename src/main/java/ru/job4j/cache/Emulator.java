package ru.job4j.cache;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Emulator {

    private String path;
    private AbstractCache<String, String> cache;

    private String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            bf.lines().forEach(sb::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void getDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsolutePath()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsolutePath()));
        }
        for (File fil : file.listFiles()) {
            cache.put(fil.getName(), this.readFile(path));
        }
    }

    public String getDate(String key) {
        return cache.get(key);
    }

    public static void main(String[] args) {

    }
}
