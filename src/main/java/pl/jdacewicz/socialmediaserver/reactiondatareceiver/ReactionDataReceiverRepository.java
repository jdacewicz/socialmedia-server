package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

interface ReactionDataReceiverRepository extends MongoRepository<Reaction, String> {

    Page<Reaction> findByArchived(boolean archived, Pageable pageable);

    Page<Reaction> findByActiveAndArchived(boolean active, boolean archived, Pageable pageable);

    List<Reaction> findAllByActiveAndArchived(boolean active, boolean archived);

    @Query("{'reactionId' : ?0}")
    @Update("{'$set': {'name': ?1}}")
    void updateReactionName(String id, String name);

    @Query("{'reactionId' : ?0}")
    @Update("{'$set': {'active': ?1}}")
    void updateReactionActive(String id, boolean active);

    @Query("{'reactionId' : ?0}")
    @Update("{'$set': {'archived': ?1}}")
    void updateReactionArchived(String id, boolean active);
}
