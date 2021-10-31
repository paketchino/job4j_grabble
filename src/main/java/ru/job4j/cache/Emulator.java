package ru.job4j.cache;

import java.io.*;
import java.util.Objects;

public class Emulator extends DirFileCache {

    private String path;

    public Emulator(String path) {
        super(path);
    }

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
        for (File fil : Objects.requireNonNull(file.listFiles())) {
            super.put(fil.getName(), readFile(fil.toString()));
        }
    }

    public void getDate(String key) {
        System.out.println(get(key));
    }

    public static void main(String[] args) {
        String path = "src/main/java/ru/job4j/cache/";
        Emulator emulator = new Emulator(path);
        emulator.getDirectory(path);
        emulator.getDate("Names.txt");
        emulator.getDate("Adress.txt");
    }
}
