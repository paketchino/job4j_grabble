package ru.job4j.quartz.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Map;

public class SqlDataTimeParser implements DataTimeParser  {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("JAN", "янв"),
            Map.entry("FEB", "фев"),
            Map.entry("MARCH", "март"),
            Map.entry("APR", "апр"),
            Map.entry("MAY", "май"),
            Map.entry("JUNE", "июнь"),
            Map.entry("JULY", "июль"),
            Map.entry("AUG", "авг"),
            Map.entry("SEP", "сент"),
            Map.entry("OCT", "окт"),
            Map.entry("NOV", "нов"),
            Map.entry("DEC", "дек")
    );

    public String getIndex(String key) {
        if (MONTHS.size() < 1) {
            throw new IllegalArgumentException();
        }
        return MONTHS.get(key);
    }

    @Override
    public LocalDateTime parse(String parse) {
        String date = parse.split(",")[0];
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM yy", Locale.ENGLISH);
        String time = parse.split(",")[1];
        if (date.contains("сегодня")) {
            date = LocalDateTime.now().format(dateTimeFormatter);
        } else if (date.contains("завтра")) {
            date = LocalDateTime.now().minusDays(1).format(dateTimeFormatter);
        } else {
            String[] dt = date.split(" ");
            dt[1] = getIndex(dt[1]);
            date = dt[0] + " " + dt[1] + " " + dt[2];
        }
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
        dateTimeFormatterBuilder.parseCaseInsensitive();
        dateTimeFormatterBuilder.appendPattern("d MMM yy 'at' HH:mm");
        DateTimeFormatter dateTimFormatter = dateTimeFormatterBuilder.toFormatter().withLocale(Locale.ENGLISH);
        return LocalDateTime.parse(date + " " + time, dateTimFormatter);
    }

}
