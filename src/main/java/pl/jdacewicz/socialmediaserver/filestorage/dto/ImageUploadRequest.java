package pl.jdacewicz.socialmediaserver.filestorage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public record ImageUploadRequest(@NonNull
                                 @Size(max = 10485760)
                                 MultipartFile file,

                                 @NotEmpty
                                 @Size(max = 600)
                                 String fileUploadDirectory) {
}
