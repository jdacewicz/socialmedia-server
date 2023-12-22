package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface BannedWordRepository extends MongoRepository<BannedWord, String> {

    Page<BannedWord> findByWordContaining(String word, Pageable pageable);
}
