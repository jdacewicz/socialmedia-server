package pl.jdacewicz.socialmediaserver.datareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DataReceiverRepository extends MongoRepository<Post, String> {
}
