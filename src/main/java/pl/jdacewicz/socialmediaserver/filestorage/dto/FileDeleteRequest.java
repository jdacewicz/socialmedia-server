package pl.jdacewicz.socialmediaserver.filestorage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record FileDeleteRequest(@NotEmpty
                                @Size(max = 120)
                                String fileName,

                                @NotEmpty
                                @Size(max = 600)
                                String folderDirectory) {
}
