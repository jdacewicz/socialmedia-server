package pl.jdacewicz.socialmediaserver.bangiver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPermanentBanRequest(@NotBlank
                                      @Size(max = 255)
                                      String reason) {
}
