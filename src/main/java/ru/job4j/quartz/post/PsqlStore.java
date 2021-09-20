package ru.job4j.quartz.post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable{

    private Connection cn;

    public PsqlStore(Properties properties) {
        try {
            Class.forName(properties.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                properties.getProperty(""),
                properties.getProperty(""),
                properties.getProperty(""));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {

    }

    @Override
    public List<Post> getALL() {
        return null;
    }

    @Override
    public Post findById(int id) {
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}
