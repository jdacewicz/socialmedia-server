package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/comments")
@RequiredArgsConstructor
public class CommentDataReceiverRestController {

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable @NotBlank String id) {
        return discussionDataReceiverFacade.getCommentById(id);
    }

    @PostMapping
    public CommentDto createComment(@RequestPart MultipartFile commentImage,
                                    @RequestPart @Valid CommentRequest commentRequest) throws IOException {
        return discussionDataReceiverFacade.createComment(commentImage, commentRequest);
    }

    @PutMapping("/{commentId}/react/{reactionId}")
    public CommentDto reactToPost(@PathVariable @NotBlank String commentId,
                                  @PathVariable @NotBlank String reactionId) {
        return discussionDataReceiverFacade.reactToComment(reactionId, commentId);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable @NotBlank String id) throws IOException {
        discussionDataReceiverFacade.deleteComment(id);
    }
}
