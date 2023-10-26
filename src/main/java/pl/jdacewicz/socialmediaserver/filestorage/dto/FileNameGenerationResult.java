package pl.jdacewicz.socialmediaserver.filestorage.dto;

import lombok.Builder;

@Builder
public record FileNameGenerationResult(String fileName) {
}
