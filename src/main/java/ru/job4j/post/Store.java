package ru.job4j.post;

import java.util.List;

public interface Store {

    void save(Post post);

    List<Post> getALL();

    Post findById(int id);
}
