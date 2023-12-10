package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface GroupedPostDataReceiverRepository extends MongoRepository<GroupedPost, String> {

    Page<GroupedPost> findByContentContaining(String phrase, Pageable pageable);

    Page<GroupedPost> findAllByCreator_UserId(String userId, Pageable pageable);
}
