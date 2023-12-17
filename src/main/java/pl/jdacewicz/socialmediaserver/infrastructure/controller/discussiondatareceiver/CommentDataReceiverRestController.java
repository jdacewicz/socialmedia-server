package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.*;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/comments")
@RequiredArgsConstructor
public class CommentDataReceiverRestController {

    final static String discussionType = "COMMENT";

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;
    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @GetMapping("/{id}")
    public DiscussionDto getCommentById(@PathVariable @NotBlank String id) {
        return discussionDataReceiverFacade.getDiscussionById(id, discussionType);
    }

    @PostMapping
    public DiscussionDto createComment(@RequestHeader("Authorization") String authorizationHeader,
                                       @RequestPart MultipartFile commentImage,
                                       @RequestPart @Valid CommentCreationRequest commentCreationRequest) throws IOException {
        return discussionDataReceiverFacade.createDiscussion(discussionType, authorizationHeader, commentImage, commentCreationRequest);
    }

    @PostMapping("/{id}/report")
    public void reportComment(@PathVariable String id,
                              @RequestHeader("Authorization") String authorizationHeader,
                              @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, authorizationHeader, reportRequest, discussionType);
    }

    @PutMapping("/{commentId}/react/{reactionId}")
    public DiscussionDto reactToComment(@PathVariable @NotBlank String commentId,
                                        @PathVariable @NotBlank String reactionId,
                                        @RequestHeader("Authorization") String authorizationHeader) {
        return discussionDataReceiverFacade.reactToDiscussion(reactionId, commentId, discussionType, authorizationHeader);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable @NotBlank String id,
                              @RequestParam @NotBlank String commentType) throws IOException {
        discussionDataReceiverFacade.deleteDiscussion(id, commentType);
    }
}
