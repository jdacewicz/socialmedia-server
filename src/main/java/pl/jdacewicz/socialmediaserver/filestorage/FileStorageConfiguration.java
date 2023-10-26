package pl.jdacewicz.socialmediaserver.filestorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FileStorageConfiguration {

    @Bean
    FileStorageFacade fileStorageFacade() {
        var imageValidator = new ImageValidator();
        return new FileStorageFacade(imageValidator);
    }
}
