package ru.job4j.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Map;

public class SqlDataTimeParser implements DataTimeParser  {
    /**
     * константы
     */
    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "JAN"),
            Map.entry("фев", "FEB"),
            Map.entry("мар", "MAR"),
            Map.entry("апр", "APR"),
            Map.entry("май", "MAY"),
            Map.entry("июн", "JUN"),
            Map.entry("июл", "JUL"),
            Map.entry("авг", "AUG"),
            Map.entry("сен", "SEP"),
            Map.entry("окт", "OCT"),
            Map.entry("ноя", "NOV"),
            Map.entry("дек", "DEC")
    );

    /**
     *
     * @param key возвращает найденный индекс
     * @return key
     */
    public String getIndex(String key) {
        if (MONTHS.size() < 1) {
            throw new IllegalArgumentException();
        }
        return MONTHS.get(key);
    }

    /**
     * Парсит дату по трем значениям
     * сегодня, завтра и дата
     * @param parse входной параметр
     * @return возвращает от парсенную дату
     */
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
        dateTimeFormatterBuilder.appendPattern("d MMM yy HH:mm");
        DateTimeFormatter dateTimFormatter = dateTimeFormatterBuilder.toFormatter().withLocale(Locale.ENGLISH);
        return LocalDateTime.parse(date + time, dateTimFormatter);
    }
}
