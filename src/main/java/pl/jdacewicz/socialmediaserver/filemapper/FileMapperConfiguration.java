package pl.jdacewicz.socialmediaserver.filemapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FileMapperConfiguration {

    @Bean
    FileMapperFacade fileMapperFacade(FileMapper fileMapper) {
        return new FileMapperFacade(fileMapper);
    }
}
