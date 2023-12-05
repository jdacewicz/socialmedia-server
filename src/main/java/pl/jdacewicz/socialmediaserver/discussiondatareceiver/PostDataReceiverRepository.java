package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface PostDataReceiverRepository extends MongoRepository<Post, String> {

    Page<Post> findByContentContaining(String phrase, Pageable pageable);

    Page<Post> findAllByCreator_UserId(String userId, Pageable pageable);
}
