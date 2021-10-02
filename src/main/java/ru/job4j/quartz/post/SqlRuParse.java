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

    /**
     * получаем список постов 5 страниц
     * @param link входный параметр, который с помощью
     * метода detail получают информацию о post
     * @return список list
     */
    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            try {
                Document doc = Jsoup.connect(link + "/" + i).get();
                Elements forumTable = doc.select(".forumTable");
                Elements tr = forumTable.select(".postslisttopic");
                for (Element trs : tr) {
                    Element td = trs.select("td").get(0).select("a").first();
                    list.add(detail(td.attr("href")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Разбивает обьявление на отдельные
     * ДЕТАЛИ в соотвествие с классом Post
     * @param link входной параметр который передает ссылку
     * на страницу
     * @return возвращает информацию о post
     */
    @Override
    public Post detail(String link) {
        Post post = new Post();
        try {
            Document document = Jsoup.connect(
                    link)
                    .get();
            String msgHeader = document.select(".messageHeader").get(0).text();
            String msgDescription = post.loadDescription(link, 1);

            String msgFooterText = document.select(".msgFooter").last().text();
            String date = msgFooterText.substring(0, msgFooterText.indexOf("[")).trim();
            LocalDateTime created =
                    dateTimeParser.parse(date);
            post = new Post(msgHeader, link, msgDescription, created);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        SqlRuParse sqlRuParse = new SqlRuParse(new SqlDataTimeParser());
        //List<Post> posts = sqlRuParse.list("https://www.sql.ru/forum/job-offers/");
        System.out.println(sqlRuParse.detail("https://www.sql.ru/forum/1338951/programmist-s"));
        //System.out.println(posts);
    }
}
