package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.LocalDateTime;
import java.time.Period;

class YearsFormatter extends LocalDateTimeFormatter {

    public YearsFormatter(LocalDateTimeFormatter next) {
        super(next);
    }

    @Override
    String format(LocalDateTime then, LocalDateTime now) {
        var period = Period.between(then.toLocalDate(), now.toLocalDate());
        if (period.getYears() > 0) {
            return String.format("%d years ago", period.getYears());
        }
        return next.format(then, now);
    }
}
