package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface BannedWordRepository extends MongoRepository<BannedWord, String> {

    Optional<BannedWord> findByWord(String word);
}
