package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.LocalDateTime;

class LocalDateTimeFormatterChain {

    private final LocalDateTimeFormatter localDateTimeFormatter;

    public LocalDateTimeFormatterChain() {
        localDateTimeFormatter = new MinutesFormatter(new HoursFormatter(new DaysFormatter(
                new MonthsFormatter(new YearsFormatter(null)))));
    }

    String format(LocalDateTime then, LocalDateTime now) {
        return localDateTimeFormatter.format(then, now);
    }
}
