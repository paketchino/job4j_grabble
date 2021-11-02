package ru.job4j.cache;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Emulator {

    private static final Boolean WATCH = true;
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

    public void getDate(String key) {
        System.out.println(cache.get(key));
    }

    public static void main(String[] args) {
        String path = "src/main/java/ru/job4j/cache/";
        Emulator emulator = new Emulator();
        System.out.println("Enter that do you want");
        Scanner scanner = new Scanner(System.in);
        String enterFile = scanner.next();
        while (!enterFile.equals(OUT)) {
            if (enterFile.equals(GETDIR)) {
                System.out.println("Введите путь директории");
                String paths = scanner.next();
                emulator.getDirectory(paths);
                if (WATCH) {
                    System.out.println("Введите файл который необходимо найти");
                    String findFile = scanner.next();
                    File file = new File(findFile);
                    if (!file.exists()) {
                        System.out.println("Данный файл отсуствует");
                        continue;
                    }
                    emulator.getDate(findFile);
                }
            }
        }
   }
}
