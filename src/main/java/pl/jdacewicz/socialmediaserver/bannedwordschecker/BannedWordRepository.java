package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.springframework.data.mongodb.repository.MongoRepository;

interface BannedWordRepository extends MongoRepository<BannedWord, String> {
}
