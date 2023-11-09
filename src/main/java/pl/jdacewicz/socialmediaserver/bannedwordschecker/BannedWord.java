package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "banned_words")
@Builder
record BannedWord(@Id
                  String wordId,

                  String creatorId,

                  String word) {
}
