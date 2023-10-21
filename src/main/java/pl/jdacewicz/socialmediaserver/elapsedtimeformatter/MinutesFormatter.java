package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.Duration;
import java.time.LocalDateTime;

class MinutesFormatter extends LocalDateTimeFormatter {

    public MinutesFormatter(LocalDateTimeFormatter next) {
        super(next);
    }

    @Override
    String format(LocalDateTime then, LocalDateTime now) {
        var duration = Duration.between(then, now);
        var minutes = duration.toMinutes();
        if (minutes > 0) {
            return String.format("%d minutes ago", minutes);
        }
        return "just now";
    }
}
