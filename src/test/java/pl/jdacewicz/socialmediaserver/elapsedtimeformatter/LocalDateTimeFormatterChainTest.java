package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeFormatterChainTest {

    LocalDateTimeFormatterChain localDateTimeFormatterChain;

    @BeforeEach
    void setUp() {
        localDateTimeFormatterChain = new LocalDateTimeFormatterChain();
    }

    @Test
    void should_return_just_now_when_formatting_by_equal_date_times() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var now = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("just now", result);
    }

    @Test
    void should_return_just_now_when_formatting_by_date_times_with_fifty_nine_seconds_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var now = LocalDateTime.of(2000, 1, 1, 0, 0, 59);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("just now", result);
    }

    @Test
    void should_return_one_minutes_ago_when_formatting_by_date_times_with_one_minute_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var now = LocalDateTime.of(2000, 1, 1, 0, 1, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("1 minutes ago", result);
    }

    @Test
    void should_return_fifty_nine_minutes_ago_when_formatting_by_date_times_with_fifty_nine_minutes_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var now = LocalDateTime.of(2000, 1, 1, 0, 59, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("59 minutes ago", result);
    }

    @Test
    void should_return_one_hours_ago_when_formatting_by_date_times_with_one_hour_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var now = LocalDateTime.of(2000, 1, 1, 1, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("1 hours ago", result);
    }

    @Test
    void should_return_twenty_three_hours_ago_when_formatting_by_date_times_with_twenty_three_hours_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0,0 );
        var now = LocalDateTime.of(2000, 1, 1, 23, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("23 hours ago", result);
    }

    @Test
    void should_return_one_days_ago_when_formatting_by_date_times_with_one_day_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        var now = LocalDateTime.of(2000, 1, 2, 0, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("1 days ago", result);
    }

    @Test
    void should_return_twenty_nine_days_ago_when_formatting_by_date_times_with_twenty_nine_days_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0);
        var now = LocalDateTime.of(2000, 1, 30, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("29 days ago", result);
    }

    @Test
    void should_return_thirty_days_ago_when_formatting_by_date_times_with_thirty_days_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0);
        var now = LocalDateTime.of(2000, 1, 31, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("30 days ago", result);
    }

    @Test
    void should_return_one_months_ago_when_formatting_by_date_times_with_one_month_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0);
        var now = LocalDateTime.of(2000, 2, 1, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("1 months ago", result);
    }

    @Test
    void should_return_eleven_months_ago_when_formatting_by_date_times_with_eleven_months_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0);
        var now = LocalDateTime.of(2000, 12, 1, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("11 months ago", result);
    }

    @Test
    void should_return_one_years_ago_when_formatting_by_date_times_with_one_year_difference() {
        //Given
        var then = LocalDateTime.of(2000, 1, 1, 0, 0);
        var now = LocalDateTime.of(2001, 1, 1, 0, 0);
        //When
        var result = localDateTimeFormatterChain.format(then, now);
        //Then
        assertEquals("1 years ago", result);
    }
}