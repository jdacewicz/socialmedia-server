package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.Duration;
import java.time.LocalDateTime;

class HoursFormatter extends LocalDateTimeFormatter {

    public HoursFormatter(LocalDateTimeFormatter next) {
        super(next);
    }

    @Override
    String format(LocalDateTime then, LocalDateTime now) {
        var duration = Duration.between(then, now);
        var hours = duration.toHours();
        if (hours > 0) {
            return String.format("%d hours ago", hours);
        }
        return next.format(then, now);
    }
}
