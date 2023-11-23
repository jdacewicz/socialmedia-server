package pl.jdacewicz.socialmediaserver.filemapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;

@Component
@RequiredArgsConstructor
class FileMapper {

    private final ServletDataExtractor servletDataExtractor;

    public File mapToFile(MapRequest mapRequest) {
        var url = getFullFileUrl(mapRequest.fileName(), mapRequest.folderDirectory());
        return File.builder()
                .url(url)
                .build();
    }

    private String getFullFileUrl(String fileName, String fileDirectory) {
        return servletDataExtractor.getFullServerUrl() + "/" + fileDirectory + "/" + fileName;
    }
}
