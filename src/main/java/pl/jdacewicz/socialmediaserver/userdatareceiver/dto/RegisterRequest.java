package pl.jdacewicz.socialmediaserver.userdatareceiver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank
                              @Email
                              String email,

                              @NotBlank
                              @Size(min = 8, max = 24)
                              String password,

                              @NotBlank
                              @Size(min = 2, max = 16)
                              String firstname,

                              @NotBlank
                              @Size(min = 2, max = 16)
                              String lastname) {
}
