package pl.jdacewicz.socialmediaserver.filestorage;

import org.springframework.context.annotation.Configuration;

@Configuration
class FileStorageConfiguration {

    FileStorageFacade fileStorageFacade(FileStorageService fileStorageService) {
        var imageValidator = new ImageValidator();
        return new FileStorageFacade(fileStorageService, imageValidator);
    }
}
