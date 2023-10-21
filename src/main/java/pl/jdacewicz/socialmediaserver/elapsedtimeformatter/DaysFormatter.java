package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.LocalDateTime;
import java.time.Period;

class DaysFormatter extends LocalDateTimeFormatter {

    public DaysFormatter(LocalDateTimeFormatter next) {
        super(next);
    }

    @Override
    String format(LocalDateTime then, LocalDateTime now) {
        var period = Period.between(then.toLocalDate(), now.toLocalDate());
        if (period.getDays() > 0) {
            return String.format("%d days ago", period.getDays());
        }
        return next.format(then, now);
    }
}
