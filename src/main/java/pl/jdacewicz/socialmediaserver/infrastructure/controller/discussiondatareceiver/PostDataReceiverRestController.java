package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class PostDataReceiverRestController {

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;
    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable @NotBlank String id,
                               @RequestParam @NotBlank String postType) {
        return discussionDataReceiverFacade.getPostById(id, postType);
    }

    @GetMapping
    public List<PostDto> getRandomPosts(@RequestParam @NotBlank String postType) {
        return discussionDataReceiverFacade.getRandomPosts(postType);
    }

    @GetMapping("/user/{userId}")
    public Page<PostDto> getPostsByUserId(@PathVariable String userId,
                                          @RequestParam @NotBlank String postType) {
        return discussionDataReceiverFacade.getPostsByUserId(userId, postType);
    }

    @PostMapping
    public PostDto createBasicPost(@RequestHeader("Authorization") String authorizationHeader,
                              @RequestPart MultipartFile postImage,
                              @RequestPart @Valid PostRequest postRequest) throws IOException {
        return discussionDataReceiverFacade.createBasicPost(authorizationHeader, postImage, postRequest);
    }

    @PostMapping("/group/{id}")
    public PostDto createGroupPost(@RequestHeader("Authorization") String authorizationHeader,
                                   @PathVariable String id,
                                   @RequestPart MultipartFile postImage,
                                   @RequestPart @Valid PostRequest postRequest) throws IOException {
        return discussionDataReceiverFacade.createGroupedPost(id, authorizationHeader, postImage, postRequest);
    }

    @PostMapping("/{id}/report")
    public void reportPost(@RequestHeader("Authorization") String authorizationHeader,
                           @PathVariable String id,
                           @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, authorizationHeader, reportRequest, "POST");
    }

    @PutMapping("/{postId}/react/{reactionId}")
    public PostDto reactToPost(@RequestHeader("Authorization") String authorizationHeader,
                               @PathVariable @NotBlank String postId,
                               @PathVariable @NotBlank String reactionId,
                               @RequestParam @NotBlank String postType) {
        return discussionDataReceiverFacade.reactToPost(reactionId, postId, postType, authorizationHeader);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable @NotBlank String id,
                           @RequestParam @NotBlank String postType) throws IOException {
        discussionDataReceiverFacade.deletePost(id, postType);
    }
}
