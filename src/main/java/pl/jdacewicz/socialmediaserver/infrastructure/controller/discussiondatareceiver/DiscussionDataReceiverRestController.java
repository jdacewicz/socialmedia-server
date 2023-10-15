package pl.jdacewicz.socialmediaserver.infrastructure.controller.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.DiscussionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

@RestController
@RequestMapping(value = "/api/discussions")
@RequiredArgsConstructor
public class DiscussionDataReceiverRestController {

    private final DiscussionDataReceiverFacade discussionDataReceiverFacade;

    @PostMapping("/posts")
    public PostDto createPost(@RequestBody PostRequest postRequest) {
        return discussionDataReceiverFacade.createPost(postRequest);
    }
}
