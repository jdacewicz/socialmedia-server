package pl.jdacewicz.socialmediaserver.filestorage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DirectoryDeleteRequest(@NotEmpty
                                     @Size(max = 600)
                                     String directory) {
}
