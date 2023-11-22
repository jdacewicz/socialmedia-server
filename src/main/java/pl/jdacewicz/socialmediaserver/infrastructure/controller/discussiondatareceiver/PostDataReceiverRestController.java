package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
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
    public PostDto getPostById(@PathVariable @NotBlank String id) {
        return discussionDataReceiverFacade.getPostById(id);
    }

    @GetMapping
    public List<PostDto> getRandomPosts() {
        return discussionDataReceiverFacade.getRandomPosts();
    }

    @PostMapping
    public PostDto createPost(@RequestPart MultipartFile postImage,
                              @RequestPart @Valid PostRequest postRequest) throws IOException {
        return discussionDataReceiverFacade.createPost(postImage, postRequest);
    }

    @PostMapping("/{id}/report")
    public void reportPost(@PathVariable String id,
                           @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, reportRequest, "POST");
    }

    @PutMapping("/{postId}/react/{reactionId}")
    public PostDto reactToPost(@PathVariable @NotBlank String postId,
                               @PathVariable @NotBlank String reactionId) {
        return discussionDataReceiverFacade.reactToPost(reactionId, postId);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable @NotBlank String id) throws IOException {
        discussionDataReceiverFacade.deletePost(id);
    }
}
