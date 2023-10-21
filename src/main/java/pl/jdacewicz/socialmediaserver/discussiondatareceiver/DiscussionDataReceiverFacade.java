package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostReactionRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

@RequiredArgsConstructor
public class DiscussionDataReceiverFacade {

    private final DiscussionDataReceiverService discussionDataReceiverService;

    public PostDto createPost(PostRequest postRequest) {
        var createdPost = discussionDataReceiverService.createPost(postRequest);
        return mapToDto(createdPost);
    }

    public PostDto reactToPost(PostReactionRequest postReactionRequest) {
        var reactedPost = discussionDataReceiverService.reactToPost(postReactionRequest);
        return mapToDto(reactedPost);
    }

    private PostDto mapToDto(Post post) {
        return PostDto.builder()
                .postId(post.postId())
                .content(post.content())
                .build();
    }
}
