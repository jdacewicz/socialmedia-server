package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataGrouperConfiguration {

    @Bean
    DataGrouperFacade dataGrouperFacade(DataGrouperService dataGrouperService) {
        return new DataGrouperFacade(dataGrouperService);
    }
}
