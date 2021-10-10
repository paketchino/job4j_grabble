package ru.job4j.post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cn;

    /**
     * Выполняет соеденение с БД
     * загружает информацию из app.properties
     * @param properties файл который он читает
     */
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

    /**
     * добавляет в БД информацию о post
     * @param post пост на сайте
     */
    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                    cn.prepareStatement("insert into post(name, link, text_post, created) values (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTittle());
            statement.setString(2, post.getLink());
            statement.setString(3, post.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf((post.getCreated())));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * получает список всех созданных постов в БД
     * @return postList список постов в БД
     */
    @Override
    public List<Post> getALL() {
        List<Post> postList = new ArrayList<>();
        try (PreparedStatement prepared = cn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = prepared.executeQuery()) {
                    while (resultSet.next()) {
                        postList.add(new Post(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("link"),
                                resultSet.getString("text_post"),
                                resultSet.getTimestamp("created").toLocalDateTime()));
                    }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postList;
    }

    /**
     * медот ищет id
     * @param id = задаваемый параметр
     * @return возвращает найденный id
     */
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
