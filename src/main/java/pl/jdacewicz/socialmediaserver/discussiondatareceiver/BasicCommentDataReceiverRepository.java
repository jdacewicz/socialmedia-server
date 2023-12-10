package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface BasicCommentDataReceiverRepository extends MongoRepository<BasicComment, String> {

    Page<BasicComment> findByContentContaining(String phrase, Pageable pageable);
}
