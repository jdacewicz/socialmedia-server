package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostReactionRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

@RequiredArgsConstructor
public class DiscussionDataReceiverFacade {

    private final DiscussionDataReceiverService discussionDataReceiverService;
    private final PostMapper postMapper;

    public PostDto getPostById(String id) {
        var foundPost = discussionDataReceiverService.getPostById(id);
        return postMapper.mapToDto(foundPost);
    }

    public PostDto createPost(PostRequest postRequest) {
        var createdPost = discussionDataReceiverService.createPost(postRequest);
        return postMapper.mapToDto(createdPost);
    }

    public PostDto reactToPost(PostReactionRequest postReactionRequest) {
        var reactionUserRequest = mapToReactionUserRequest(postReactionRequest);
        var reactionUser = reactionUserFacade.createReactionUser(reactionUserRequest);
        var reactedPost = discussionDataReceiverService.addReactionUserToPostById(postReactionRequest.postId(), reactionUser);
        return postMapper.mapToDto(reactedPost);
    }

    private ReactionUserRequest mapToReactionUserRequest(PostReactionRequest postReactionRequest) {
        return ReactionUserRequest.builder()
                .reactionId(postReactionRequest.reactionId())
                .build();
    }
}
