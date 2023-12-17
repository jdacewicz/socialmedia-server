package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreationRequest extends DiscussionCreationRequest {

    @NotBlank
    private String postId;
}
