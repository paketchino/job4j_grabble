package ru.job4j.cache;

import java.io.*;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Emulator {

    private Boolean watch = true;
    private static final String GETDIR = "getDir";
    private static final String OUT = "out";

    private HashMap<String, String> cache = new HashMap<>();

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
        for (File fil : Objects.requireNonNull(file.listFiles())) {
            cache.put(fil.getName(), readFile(fil.toString()));
        }
    }

    public String getDate(String key, String path) {
        AbstractCache<String, String> cache = new DirFileCache(path);
        return cache.get(key);
    }

    public static void main(String[] args) {
        String path = "src/main/java/ru/job4j/cache/";
        Emulator emulator = new Emulator();
        System.out.println("Enter that do you want");
        Scanner scanner = new Scanner(System.in);
        String enterFile = scanner.next();
        while (!enterFile.equals(OUT)) {
            if (enterFile.equals(GETDIR)) {
                System.out.println("Введите путь кешируемой директории");
                String paths = scanner.next();
                if (paths == null || paths.isEmpty()) {
                    System.out.println("Такой директории нет, введите другую директорию");
                } else {
                    emulator.getDirectory(paths);
                }
                if (emulator.watch) {
                    System.out.println("Укажите файл из кеша");
                    String file = scanner.next();
                        String input = emulator.getDate(file, paths);
                        System.out.println(input);
                        emulator.watch = false;
                }
            }
        }
    }
}
