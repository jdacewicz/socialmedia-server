package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reactions")
@Builder
record Reaction(@Id
                String reactionId,

                String name,

                String imageName,

                boolean active,

                boolean archived) {

    private final static String MAIN_DIRECTORY = "data/reactions";

    String getReactionImageDirectory() {
        return String.format("%s/%s", getReactionFolderDirectory(), imageName);
    }

    String getReactionFolderDirectory() {
        return String.format("%s/%s", MAIN_DIRECTORY, reactionId);
    }
}
