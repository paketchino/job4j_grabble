package ru.job4j.quartz.utils;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class SqlDataTimeParserTest {

    @Test
    public void whenNeedCheckDataTime() {
        LocalDateTime sqlDataTimeParser = new SqlDataTimeParser().parse("2 дек 19, 22:29");
        LocalDateTime res = LocalDateTime.of(
                LocalDate.of(2019, 12, 2),
                LocalTime.of(22, 29)
        );
        Assert.assertEquals(res, sqlDataTimeParser);
    }

    @Test
    public void whenNeedCheckDataTimeNow() {
        LocalDateTime dateTime = new SqlDataTimeParser().parse("сегодня, 02:30");
        LocalDateTime res  = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(2, 30)
        );
        Assert.assertEquals(res, dateTime);
    }

    @Test
    public void whenNeedCheckDateTimeTomorrow() {
        LocalDateTime dateTime = new SqlDataTimeParser().parse("завтра, 19:23");
        LocalDateTime res = LocalDateTime.of(
                LocalDate.now().minusDays(1),
                LocalTime.of(19, 23)
        );
        assertEquals(res, dateTime);
    }
}