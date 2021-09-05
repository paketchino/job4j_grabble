package ru.job4j.quartz.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.quartz.utils.SqlDataTimeParser;

import java.io.IOException;

public class SqlRuParse {

    private static int index = 1;

    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers";
        for (int i = 1; i <= 5; i++) {
            parser(url + i, index++);
        }
    }

    private static void parser(String url, int index) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements row = document.select(".postslisttopic");
            System.out.println("Страница № " + index);
            for (Element el : row) {
                Element href = el.child(0);
                System.out.println(href.attr(".href"));
                System.out.println(href.text());
                System.out.println(el.parent().child(5).text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
