package ru.job4j.quartz.post;
//
import ru.job4j.quartz.utils.SqlDataTimeParser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cn;

    public PsqlStore(Properties properties) {
        try (var loader = PsqlStore.class.getClassLoader().getResourceAsStream("app.properties")) {
            Class.forName(properties.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("login"),
                properties.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                    cn.prepareStatement("insert into post(name, text_post, link, created) values (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTittle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf((post.getCreated())));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(post.getId()));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getALL() {
        List<Post> postList = new ArrayList<>();
        try (PreparedStatement prepared = cn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = prepared.executeQuery()) {
                    while (resultSet.next()) {
                        postList.add(new Post(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("text_post"),
                                resultSet.getString("link"),
                                resultSet.getTimestamp("created").toLocalDateTime()));
                    }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement prepared = cn.prepareStatement("select from post where id = ?")) {
            prepared.setInt(1, id);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text_post"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }
}
