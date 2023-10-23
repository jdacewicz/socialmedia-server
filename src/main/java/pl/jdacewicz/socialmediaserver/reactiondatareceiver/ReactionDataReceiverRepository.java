package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

interface ReactionDataReceiverRepository extends MongoRepository<Reaction, String> {

    @Query("{'reactionId' : ?0}")
    @Update("{'$set': {'active': ?1}}")
    void updateReactionActive(String id, boolean active);

    @Query("{'reactionId' : ?0}")
    @Update("{'$set': {'archived': ?1}}")
    void updateReactionArchived(String id, boolean active);
}
