package ru.job4j.quartz.post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.quartz.utils.DataTimeParser;
import ru.job4j.quartz.utils.SqlDataTimeParser;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Load {

    private final DataTimeParser dateTimeParser;

    public Load(DataTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public void load(String link, String att, int index) {
        try {
            Document doc = Jsoup.connect(
                    link)
                    .get();
            Elements row = doc.select(att);
            Element att1 = row.get(index);
            System.out.println(att1.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SqlDataTimeParser sql = new SqlDataTimeParser();
        Load load = new Load(sql);
        Stream.of("10", "11", "12")
                        .map(x -> Integer.parseInt(x, 16))
                        .forEach(System.out::println);
    }
}