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
            Elements msgTable = doc.select(".postslisttopic");
            for (Element el : msgTable) {
                Element href = el.child(0);
                href.attr("msgTable");
                href.text();
                list.add(detail(link));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
