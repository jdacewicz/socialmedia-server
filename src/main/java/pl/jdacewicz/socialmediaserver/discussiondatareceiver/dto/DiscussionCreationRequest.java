package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionCreationRequest {

    @NotBlank
    @Size(max = 255)
    private String content;
}
