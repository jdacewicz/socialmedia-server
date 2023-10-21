package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import java.time.LocalDateTime;

abstract class LocalDateTimeFormatter {

    LocalDateTimeFormatter next;

    LocalDateTimeFormatter(LocalDateTimeFormatter next) {
        this.next = next;
    }

    abstract String format(LocalDateTime then, LocalDateTime now);
}
