package ru.job4j.quartz.post;
//
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
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

    public Post(String tittle, String link, String description, LocalDateTime created) {
        this.tittle = tittle;
        this.link = link;
        this.description = description;
        this.created = created;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
      this.created = created;
    }

    public String getDescription(String link, int index) {
        return loadDescription(link, index);
    }

    public String loadDescription(String link, int index) {
        String text = null;
        try {
            Document doc = Jsoup.connect(
                    link)
                    .get();
            Elements row = doc.select(".msgBody");
            Element att1 = row.get(index);
            text = att1.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(tittle, post.tittle)
                && Objects.equals(link, post.link)
                && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tittle, link, created);
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
