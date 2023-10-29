package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequest(@NotBlank
                          @Size(max = 255)
                          String content) {
}
