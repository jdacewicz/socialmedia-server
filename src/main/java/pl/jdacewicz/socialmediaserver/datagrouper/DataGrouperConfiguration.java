package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;

@Configuration
class DataGrouperConfiguration {

    @Bean
    DataGrouperFacade dataGrouperFacade(DataGrouperService dataGrouperService, FileStorageFacade fileStorageFacade) {
        return new DataGrouperFacade(dataGrouperService, fileStorageFacade);
    }
}
