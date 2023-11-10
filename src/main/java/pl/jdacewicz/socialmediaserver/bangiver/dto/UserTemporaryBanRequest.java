package pl.jdacewicz.socialmediaserver.bangiver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UserTemporaryBanRequest(@NotNull
                                      LocalDateTime to,

                                      @NotBlank
                                      @Size(max = 255)
                                      String reason) {
}
