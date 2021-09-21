package ru.job4j.quartz.post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.quartz.html.SqlRuParseDateTime;
import ru.job4j.quartz.utils.DataTimeParser;
import ru.job4j.quartz.utils.SqlDataTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        for (int i = 0; i <= 5; i++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link).get();
                Elements forumTable = doc.select(".forumTable");
                Elements tr = forumTable.select("tr");
                for (Element trs : tr) {
                    Elements td = trs.select("td");
                    list.add(detail(td.get(1).child(0).attr("href")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    @Override
    public Post detail(String link) {
        Post post = null;
        try {
            Document document = Jsoup.connect(
                    link)
                    .get();
            String msgHeader = document.select(".messageHeader").get(1).text();
            String msgDescription = document.select(".msgBody").get(1).text();
            String msgFooter = document.select(".msgFooter").get(1)
                    .ownText().replace(" [] |", "");
            LocalDateTime created =
                    dateTimeParser.parse(msgFooter);
            post = new Post(msgHeader, link, msgDescription, created);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        SqlDataTimeParser sql = new SqlDataTimeParser();

        SqlRuParse sqlRuParse = new SqlRuParse(sql);
        sqlRuParse.list("https://www.sql.ru/forum/job-offers/1");
    }
}
