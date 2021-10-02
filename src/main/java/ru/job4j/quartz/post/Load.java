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

//    public Post detail(String link) {
//        SqlDataTimeParser sqlDataTimeParser = new SqlDataTimeParser();
//        Post post = null;
//        try {
//            Document document = Jsoup.connect(
//                    link)
//                    .get();
//            String msgHeader = document.select(".messageHeader").get(1).text();
//            String msgDescription = document.select(".msgBody").get(1).text();
//            String msgFooterText = document.select(".msgFooter").last().text();
//            System.out.println(msgFooterText);
//            String date = msgFooterText.substring(0, msgFooterText.indexOf("[")).trim();
//            System.out.println(date);
//            String msgFooter2 = document.select(".msgFooter").get(1).ownText().replace("[] |", "");
//            LocalDateTime created = sqlDataTimeParser.parse(date);
//            post = new Post(msgHeader, link, msgDescription, created);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return post;
//    }
//
//
//    public List<Post> list(String link) {
//        List<Post> list = new ArrayList<>();
//        try {
//            Document doc = Jsoup.connect(link)
//                    .get();
//            Elements post = doc.select(".postslisttopic");
//            for (Element el : post) {
//                Element td = el.select("td").get(0).select("a").first();
//                list.add(detail(td.attr("href")));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    public static void main(String[] args) {
        SqlDataTimeParser sql = new SqlDataTimeParser();
        Load load = new Load(sql);
        Stream.of("10", "11", "12")
                        .map(x -> Integer.parseInt(x, 16))
                        .forEach(System.out::println);
//        System.out.println(load.detail("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"));
//        System.out.println(load.list("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"));
    }
}