package ru.job4j.post;

import java.util.List;

public interface Parse {

    List<Post> list(String link);

    Post detail(String link);
}
