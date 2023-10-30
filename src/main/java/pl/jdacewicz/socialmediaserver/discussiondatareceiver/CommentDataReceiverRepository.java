package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

interface CommentDataReceiverRepository extends MongoRepository<Comment, String> {

    Set<Comment> findByContentContaining(String phrase);
}
