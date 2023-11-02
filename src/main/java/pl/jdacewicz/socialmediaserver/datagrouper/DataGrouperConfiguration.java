package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;

@Configuration
class DataGrouperConfiguration {

    @Bean
    DataGrouperFacade dataGrouperFacade(FileStorageFacade fileStorageFacade,
                                        DataGrouperServiceFactory dataGrouperServiceFactory,
                                        GroupMapper groupMapper) {
        return new DataGrouperFacade(fileStorageFacade, dataGrouperServiceFactory, groupMapper);
    }
}
