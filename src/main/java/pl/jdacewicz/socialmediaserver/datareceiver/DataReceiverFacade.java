package pl.jdacewicz.socialmediaserver.datareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.datareceiver.dto.PostRequest;

@RequiredArgsConstructor
public class DataReceiverFacade {

    private final DataReceiverService dataReceiverService;

    public PostDto createPost(PostRequest postRequest) {
        var createdPost = dataReceiverService.createPost(postRequest);
        return new PostDto(createdPost.postId(), createdPost.content());
    }
}
