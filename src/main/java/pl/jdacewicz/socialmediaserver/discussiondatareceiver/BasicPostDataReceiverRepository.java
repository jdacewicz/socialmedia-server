package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface BasicPostDataReceiverRepository extends MongoRepository<BasicPost, String> {

    Page<BasicPost> findByContentContaining(String phrase, Pageable pageable);

    Page<BasicPost> findAllByCreator_UserId(String userId, Pageable pageable);
}
