package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface CommentDataReceiverRepository extends MongoRepository<Comment, String> {
}
