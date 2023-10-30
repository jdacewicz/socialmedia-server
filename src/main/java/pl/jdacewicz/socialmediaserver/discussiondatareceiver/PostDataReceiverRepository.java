package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

interface PostDataReceiverRepository extends MongoRepository<Post, String> {

    Set<Post> findByContentContaining(String phrase);
}
