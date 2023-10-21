package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.LocalDateTime;

class LocalDateTimeFormatterChain {

    private final LocalDateTimeFormatter localDateTimeFormatter;

    public LocalDateTimeFormatterChain() {
        localDateTimeFormatter = new YearsFormatter(new MonthsFormatter(new DaysFormatter(new HoursFormatter(
                new MinutesFormatter(null)))));
    }

    String format(LocalDateTime then, LocalDateTime now) {
        return localDateTimeFormatter.format(then, now);
    }
}
