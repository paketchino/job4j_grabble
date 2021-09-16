package ru.job4j.quartz.post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.quartz.utils.DataTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DataTimeParser dateTimeParser;

    public SqlRuParse(DataTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link)
                    .get();
            Elements row = doc.select("msgTable");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            Document document = Jsoup.connect(link).get();
            Elements row = document.select(".msgTable");
            for (Element rows : row) {
                Element parent = rows.parent();
                System.out.println(parent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }
}
