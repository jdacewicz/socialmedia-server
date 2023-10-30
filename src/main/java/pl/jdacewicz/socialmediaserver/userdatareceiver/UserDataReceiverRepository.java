package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.Set;

interface UserDataReceiverRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Set<User> findAllByFirstnameInAndLastnameIn(Set<String> firstnames, Set<String> lastnames);
}
