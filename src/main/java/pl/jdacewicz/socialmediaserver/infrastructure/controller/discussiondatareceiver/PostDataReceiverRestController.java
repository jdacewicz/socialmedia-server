package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostReactionRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class PostDataReceiverRestController {

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable String id) {
        return discussionDataReceiverFacade.getPostById(id);
    }

    @PostMapping
    public PostDto createPost(@RequestPart MultipartFile postImage,
                              @RequestPart PostRequest postRequest) throws IOException {
        return discussionDataReceiverFacade.createPost(postImage, postRequest);
    }

    @PutMapping("/{postId}/react/{reactionId}")
    public PostDto reactToPost(@PathVariable String postId,
                               @PathVariable String reactionId) {
        var postReactionRequest = PostReactionRequest.builder()
                .postId(postId)
                .reactionId(reactionId)
                .build();
        return discussionDataReceiverFacade.reactToPost(postReactionRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable String id) throws IOException {
        discussionDataReceiverFacade.deletePost(id);
    }
}
