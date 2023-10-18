package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ReactionDataReceiverRepository extends MongoRepository<Reaction, String> {
}
