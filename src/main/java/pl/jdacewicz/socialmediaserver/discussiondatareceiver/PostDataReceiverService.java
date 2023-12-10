package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

interface PostDataReceiverService<T extends Post> extends DiscussionDataReceiverService<T> {

    List<T> getRandomPosts();

    Page<T> getPostsByCreatorUserId(String userId, Pageable pageable);

    void updatePost(T post);
}
