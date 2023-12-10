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
    public CommentDto getCommentById(@PathVariable @NotBlank String id,
                                     @RequestParam @NotBlank String commentType) {
        return discussionDataReceiverFacade.getCommentById(id, commentType);
    }

    @PostMapping("/post/{postId}")
    public CommentDto createBasicComment(@RequestHeader("Authorization") String authorizationHeader,
                                         @PathVariable @NotBlank String postId,
                                         @RequestPart MultipartFile commentImage,
                                         @RequestPart @Valid CommentRequest commentRequest) throws IOException {
        return discussionDataReceiverFacade.createBasicComment(postId, authorizationHeader, commentImage, commentRequest);
    }

    @PostMapping("/group/{groupId}/post/{postId}")
    public CommentDto createGroupComment(@RequestHeader("Authorization") String authorizationHeader,
                                         @PathVariable @NotBlank String postId,
                                         @PathVariable @NotBlank String groupId,
                                         @RequestPart MultipartFile commentImage,
                                         @RequestPart @Valid CommentRequest commentRequest) throws IOException {
        return discussionDataReceiverFacade.createGroupedComment(postId, groupId, authorizationHeader, commentImage, commentRequest);
    }

    @PostMapping("/{id}/report")
    public void reportComment(@RequestHeader("Authorization") String authorizationHeader,
                              @PathVariable String id,
                              @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, authorizationHeader, reportRequest, "COMMENT");
    }

    @PutMapping("/{commentId}/react/{reactionId}")
    public CommentDto reactToComment(@RequestHeader("Authorization") String authorizationHeader,
                                          @PathVariable @NotBlank String commentId,
                                          @PathVariable @NotBlank String reactionId,
                                          @RequestParam @NotBlank String commentType) {
        return discussionDataReceiverFacade.reactToComment(reactionId, commentId, commentType, authorizationHeader);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable @NotBlank String id,
                              @RequestParam @NotBlank String commentType) throws IOException {
        discussionDataReceiverFacade.deleteComment(id, commentType);
    }
}
