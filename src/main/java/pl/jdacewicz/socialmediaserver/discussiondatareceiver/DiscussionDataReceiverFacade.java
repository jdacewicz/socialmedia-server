package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

@RequiredArgsConstructor
public class DiscussionDataReceiverFacade {

    private final DiscussionDataReceiverService discussionDataReceiverService;

    public PostDto createPost(PostRequest postRequest) {
        var createdPost = discussionDataReceiverService.createPost(postRequest);
        return new PostDto(createdPost.postId(), createdPost.content());
    }
}
