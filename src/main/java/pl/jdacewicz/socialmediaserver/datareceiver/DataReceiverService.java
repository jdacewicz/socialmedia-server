package pl.jdacewicz.socialmediaserver.datareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.datareceiver.dto.PostRequest;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DataReceiverService {

    private final DataReceiverRepository dataReceiverRepository;

    Post createPost(PostRequest postRequest) {
        var post = Post.builder()
                .postId("")
                .content(postRequest.content())
                .creationDateTime(LocalDateTime.now())
                .build();
        return dataReceiverRepository.save(post);
    }
}
