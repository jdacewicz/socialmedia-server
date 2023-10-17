package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserDataReceiverRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
