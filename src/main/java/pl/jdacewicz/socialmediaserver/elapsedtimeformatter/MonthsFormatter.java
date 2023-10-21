package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.LocalDateTime;
import java.time.Period;

class MonthsFormatter extends LocalDateTimeFormatter {

    public MonthsFormatter(LocalDateTimeFormatter next) {
        super(next);
    }

    @Override
    String format(LocalDateTime then, LocalDateTime now) {
        var period = Period.between(then.toLocalDate(), now.toLocalDate());
        if (period.getMonths() > 0) {
            return String.format("%d months ago", period.getMonths());
        }
        return next.format(then, now);
    }
}
