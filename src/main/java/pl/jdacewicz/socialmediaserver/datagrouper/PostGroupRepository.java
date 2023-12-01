package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

interface PostGroupRepository extends MongoRepository<PostGroup, String> {

    @Query("{ 'participants.userId': { '$in': [?0 ] } }")
    Page<PostGroup> findByParticipants(String participantId, Pageable pageable);
}
