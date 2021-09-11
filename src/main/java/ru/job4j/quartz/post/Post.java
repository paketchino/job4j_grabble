package ru.job4j.quartz.post;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private int id;

    private String tittle;

    private String link;

    private String description;

    private LocalDateTime created;

    public Post() {
    }

    public Post(int id) {
        this.id = id;
    }

    public Post(int id, String tittle) {
        this.id = id;
        this.tittle = tittle;
    }

    public Post(int id, String tittle, String link) {
        this.id = id;
        this.tittle = tittle;
        this.link = link;
    }

    public Post(int id, String tittle, String link, String description) {
        this.id = id;
        this.tittle = tittle;
        this.link = link;
        this.description = description;
    }

    public Post(int id, String tittle, String link, String description, LocalDateTime created) {
        this.id = id;
        this.tittle = tittle;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getTittle() {
        return tittle;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }


    @Override
    public String toString() {
        return "Post{" + "id="
                + id + ", tittle='"
                + tittle + '\'' + ", link='"
                + link + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + '}';
    }
}
