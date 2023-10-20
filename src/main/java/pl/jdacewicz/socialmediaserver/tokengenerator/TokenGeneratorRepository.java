package pl.jdacewicz.socialmediaserver.tokengenerator;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

interface TokenGeneratorRepository extends MongoRepository<Token, String> {

    Optional<Token> findByCode(String code);

    List<Token> findByUserId(String userId);
}
