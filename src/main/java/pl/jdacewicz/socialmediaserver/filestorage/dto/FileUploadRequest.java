package pl.jdacewicz.socialmediaserver.filestorage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FileUploadRequest(@NotEmpty
                                @Size(max = 600)
                                String fileName,

                                @NotEmpty
                                @Size(max = 600)
                                String fileUploadDirectory) {
}
