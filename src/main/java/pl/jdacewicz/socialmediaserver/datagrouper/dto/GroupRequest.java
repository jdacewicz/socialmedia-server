package pl.jdacewicz.socialmediaserver.datagrouper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequest(@NotBlank
                           @Size(max = 32)
                           String name) {
}
