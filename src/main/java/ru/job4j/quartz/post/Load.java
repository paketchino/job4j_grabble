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
import java.util.List;

public class Load {

    private DataTimeParser dateTimeParser;

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

    public Post detail(String link) {
        Post post = null;
        try {
            Document document = Jsoup.connect(
                    link)
                    .get();
            String msgHeader = document.select(".messageHeader").get(1).text();
            String msgDescription = document.select(".msgBody").get(1).text();
            String msgFooter = document.select(".msgFooter").get(1).ownText().replace(" [] |", "");
            LocalDateTime created =
                    dateTimeParser.parse(msgFooter);
            post = new Post(msgHeader, link, msgDescription, created);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }


    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link)
                    .get();
            Elements post = doc.select(".postslisttopic");
            for (Element el : post) {
                Element href = el.child(0);
                list.add(detail(href.attr("href")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        SqlDataTimeParser sql = new SqlDataTimeParser();
        Load load = new Load(sql);
        System.out.println(load.detail("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"));
        System.out.println(load.list("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"));
    }
}