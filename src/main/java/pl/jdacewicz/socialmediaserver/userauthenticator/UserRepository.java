package pl.jdacewicz.socialmediaserver.userauthenticator;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
