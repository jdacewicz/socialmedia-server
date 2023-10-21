package pl.jdacewicz.socialmediaserver.elapsedtimeformatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ElapsedDateTimeFormatterConfiguration {

    @Bean
    ElapsedDateTimeFormatterFacade elapsedDateTimeFormatterFacade() {
        var localDateTimeFormatterChain = new LocalDateTimeFormatterChain();
        return new ElapsedDateTimeFormatterFacade(localDateTimeFormatterChain);
    }
}
