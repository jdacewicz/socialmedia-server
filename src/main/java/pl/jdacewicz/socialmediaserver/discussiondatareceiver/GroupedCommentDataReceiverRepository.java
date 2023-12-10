package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface GroupedCommentDataReceiverRepository extends MongoRepository<GroupedComment, String> {

    Page<GroupedComment> findByContentContaining(String phrase, Pageable pageable);
}
