package ru.job4j.quartz.post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class Load {

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

        try {
            Document document = Jsoup.connect("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").get();
            Elements row = document.select(".msgTable");
            for (Element rows : row) {
                Element parent = rows.parent();
                System.out.println(parent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        Load loader = new Load();
//        loader.load("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t",
//                ".msgBody",
//                1
//        );
    }
}
