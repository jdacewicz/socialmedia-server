package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface CommentDataReceiverRepository extends MongoRepository<Comment, String> {

    Page<Comment> findByContentContaining(String phrase, Pageable pageable);
}
