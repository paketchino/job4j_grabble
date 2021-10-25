package ru.job4j.garbage;
import static com.carrotsearch.sizeof.RamUsageEstimator.sizeOf;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() {
        System.out.printf("Removed %s %n", name);
    }

}
