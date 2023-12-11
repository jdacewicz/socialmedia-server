package pl.jdacewicz.socialmediaserver.datasearcher.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SearchRequest(@NotBlank
                            @Size(min = 2, max = 60)
                            String phrase) {
}
