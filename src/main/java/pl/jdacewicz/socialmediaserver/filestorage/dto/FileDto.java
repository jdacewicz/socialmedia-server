package pl.jdacewicz.socialmediaserver.filestorage.dto;

import lombok.Builder;

@Builder
public record FileDto(String fileName,
                      String fileDirectory) {
}
