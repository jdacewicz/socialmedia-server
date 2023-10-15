package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DiscussionDataReceiverRepository extends MongoRepository<Post, String> {
}
