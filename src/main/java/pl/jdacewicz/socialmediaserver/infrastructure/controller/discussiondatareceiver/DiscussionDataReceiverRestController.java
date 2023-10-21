package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostReactionRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

@RestController
@RequestMapping(value = "/api/discussions")
@RequiredArgsConstructor
public class DiscussionDataReceiverRestController {

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    @GetMapping("/posts/{id}")
    public PostDto getPostById(@PathVariable String id) {
        return discussionDataReceiverFacade.getPostById(id);
    }

    @PostMapping("/posts")
    public PostDto createPost(@RequestBody PostRequest postRequest) {
        return discussionDataReceiverFacade.createPost(postRequest);
    }

    @PutMapping("/posts/{postId}/react/{reactionId}")
    public PostDto reactToPost(@PathVariable String postId,
                               @PathVariable String reactionId) {
        var postReactionRequest = PostReactionRequest.builder()
                .postId(postId)
                .reactionId(reactionId)
                .build();
        return discussionDataReceiverFacade.reactToPost(postReactionRequest);
    }
}
