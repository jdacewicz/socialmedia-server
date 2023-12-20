package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

interface PostDataReceiverService<T extends Post> extends DiscussionDataReceiverService<T> {

    List<T> getRandomPosts();

    Page<T> getPostsByCreatorUserId(String userId, Pageable pageable);

    Set<Comment> getCommentsByPostId(String postId, int commentQuantity);

    void updatePost(T post);
}
