package pl.jdacewicz.socialmediaserver.tokengenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface TokenGeneratorRepository extends MongoRepository<Token, String> {

    Optional<Token> findByCode(String code);

    List<Token> findByUserId(String userId);
}
