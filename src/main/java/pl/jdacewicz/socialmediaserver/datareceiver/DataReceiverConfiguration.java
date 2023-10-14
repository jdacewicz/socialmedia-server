package pl.jdacewicz.socialmediaserver.datareceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataReceiverConfiguration {

    @Bean
    public DataReceiverFacade dataReceiverFacade(DataReceiverService dataReceiverService) {
        return new DataReceiverFacade(dataReceiverService);
    }
}
