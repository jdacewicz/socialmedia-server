package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

interface PostDataReceiverService<T extends Post> {

    List<T> getRandomPosts();

    Page<T> getPostsByCreatorUserId(String userId, Pageable pageable);

    T createPost(String content, String imageName, String authenticationHeader);

    void updatePost(T post);
}
