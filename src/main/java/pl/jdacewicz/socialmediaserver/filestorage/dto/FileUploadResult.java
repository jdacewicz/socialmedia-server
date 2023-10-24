package pl.jdacewicz.socialmediaserver.filestorage.dto;

import lombok.Builder;

@Builder
public record FileUploadResult(String fileName,
                               String folderDirectory,
                               String message) {
}
