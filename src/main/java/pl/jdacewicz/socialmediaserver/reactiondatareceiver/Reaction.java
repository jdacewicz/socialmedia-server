package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reactions")
@Builder
record Reaction(@Id
                String reactionId,

                @NotBlank
                @Indexed(unique = true)
                @Size(min = 2, max = 32)
                String name,

                String imageName,

                boolean active,

                boolean archived) {

    final static String MAIN_DIRECTORY = "data/reactions";

    String getReactionImageDirectory() {
        return String.format("%s/%s", getReactionFolderDirectory(), imageName);
    }

    String getReactionFolderDirectory() {
        return String.format("%s/%s", MAIN_DIRECTORY, reactionId);
    }
}
