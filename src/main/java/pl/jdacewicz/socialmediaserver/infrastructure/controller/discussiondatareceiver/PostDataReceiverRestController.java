package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionCreationRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionDto;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class PostDataReceiverRestController {

    final static String discussionType = "BASIC_POST";

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;
    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @GetMapping("/{id}")
    public DiscussionDto getBasicPostById(@PathVariable @NotBlank String id) {
        return discussionDataReceiverFacade.getDiscussionById(id, discussionType);
    }

    @GetMapping
    public List<DiscussionDto> getRandomBasicPosts() {
        return discussionDataReceiverFacade.getRandomPosts(discussionType);
    }

    @GetMapping("/users/{userId}")
    public Page<DiscussionDto> getBasicPostsByUserId(@PathVariable String userId,
                                                     @RequestParam int pageNumber,
                                                     @RequestParam int pageSize) {
        return discussionDataReceiverFacade.getPostsByUserId(userId, discussionType, pageNumber, pageSize);
    }

    @PostMapping
    public DiscussionDto createBasicPost(@RequestHeader("Authorization") String authorizationHeader,
                                         @RequestPart MultipartFile postImage,
                                         @RequestPart @Valid DiscussionCreationRequest discussionCreationRequest) throws IOException {
        return discussionDataReceiverFacade.createDiscussion(discussionType, authorizationHeader, postImage, discussionCreationRequest);
    }

    @PostMapping("/{id}/report")
    public void reportBasicPost(@PathVariable String id,
                                @RequestHeader("Authorization") String authorizationHeader,
                                @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, authorizationHeader, reportRequest, discussionType);
    }

    @PutMapping("/{postId}/react/{reactionId}")
    public DiscussionDto reactToBasicPost(@RequestHeader("Authorization") String authorizationHeader,
                                          @PathVariable @NotBlank String postId,
                                          @PathVariable @NotBlank String reactionId) {
        return discussionDataReceiverFacade.reactToDiscussion(reactionId, postId, discussionType, authorizationHeader);
    }

    @DeleteMapping("/{id}")
    public void deleteBasicPost(@PathVariable @NotBlank String id) throws IOException {
        discussionDataReceiverFacade.deleteDiscussion(id, discussionType);
    }
}
