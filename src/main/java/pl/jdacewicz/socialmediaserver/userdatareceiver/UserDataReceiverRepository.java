package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserDataReceiverRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
