package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.elapsedtimeformatter.dto.ElapsedDateTime;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ElapsedDateTimeFormatterFacade {

    private final LocalDateTimeFormatterChain localDateTimeFormatterChain;

    public ElapsedDateTime formatDateTime(LocalDateTime then) {
        var formattedDateTime = localDateTimeFormatterChain.format(then, LocalDateTime.now());
        return new ElapsedDateTime(formattedDateTime);
    }
}
