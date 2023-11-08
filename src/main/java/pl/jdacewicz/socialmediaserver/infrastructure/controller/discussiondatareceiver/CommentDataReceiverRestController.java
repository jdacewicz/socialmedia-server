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

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;
    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable @NotBlank String id) {
        return discussionDataReceiverFacade.getCommentById(id);
    }

    @PostMapping
    public CommentDto createComment(@RequestPart MultipartFile commentImage,
                                    @RequestPart @Valid CommentRequest commentRequest) throws IOException {
        return discussionDataReceiverFacade.createComment(commentImage, commentRequest);
    }

    @PostMapping("/{id}/report")
    public void reportComment(@PathVariable String id,
                              @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, reportRequest, "COMMENT");
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
