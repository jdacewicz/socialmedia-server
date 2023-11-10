package pl.jdacewicz.socialmediaserver.bannedwordschecker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "banned_words")
@Builder
record BannedWord(@Id
                  String wordId,

                  @NotBlank
                  String creatorId,

                  @NotBlank
                  @Size(min = 2, max = 22)
                  @Indexed(unique = true)
                  String word) {
}
