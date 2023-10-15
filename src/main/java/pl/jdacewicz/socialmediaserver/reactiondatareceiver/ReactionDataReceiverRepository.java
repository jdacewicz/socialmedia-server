package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ReactionDataReceiverRepository extends MongoRepository<Reaction, String> {
}
