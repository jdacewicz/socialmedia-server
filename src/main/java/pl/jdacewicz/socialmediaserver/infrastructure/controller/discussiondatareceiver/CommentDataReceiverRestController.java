package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

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

    @PostMapping
    public CommentDto createComment(@RequestPart MultipartFile commentImage,
                                    @RequestPart CommentRequest commentRequest) throws IOException {
        return discussionDataReceiverFacade.createComment(commentImage, commentRequest);
    }

    @PutMapping("/{commentId}/react/{reactionId}")
    public CommentDto reactToPost(@PathVariable String commentId,
                                  @PathVariable String reactionId) {
        var commentReactionRequest = CommentReactionRequest.builder()
                .commentId(commentId)
                .reactionId(reactionId)
                .build();
        return discussionDataReceiverFacade.reactToComment(commentReactionRequest);
    }
}
