package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

interface PostDataReceiverRepository extends MongoRepository<Post, String> {
}
