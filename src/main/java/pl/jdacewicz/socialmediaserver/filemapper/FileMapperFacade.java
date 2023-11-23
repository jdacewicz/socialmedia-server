package pl.jdacewicz.socialmediaserver.filemapper;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;

@RequiredArgsConstructor
public class FileMapperFacade {

    private final FileMapper fileMapper;

    public File mapToFile(MapRequest mapRequest) {
        return fileMapper.mapToFile(mapRequest);
    }
}
