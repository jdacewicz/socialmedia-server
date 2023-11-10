package pl.jdacewicz.socialmediaserver.bannedwordschecker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BanWordRequest(@NotBlank
                             @Size(min = 2, max = 22)
                             String word) {
}
