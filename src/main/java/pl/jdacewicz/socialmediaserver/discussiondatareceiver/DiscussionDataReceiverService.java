package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DiscussionDataReceiverService {

    private final DiscussionDataReceiverRepository discussionDataReceiverRepository;

    Post createPost(PostRequest postRequest) {
        var post = Post.builder()
                .content(postRequest.content())
                .creationDateTime(LocalDateTime.now())
                .build();
        return discussionDataReceiverRepository.save(post);
    }
}
