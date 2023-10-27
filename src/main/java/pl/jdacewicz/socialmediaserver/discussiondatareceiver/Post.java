package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.LinkedList;

@Document(collection = "posts")
@Getter
@SuperBuilder
class Post extends Discussion<Post> {

    private final static String MAIN_DIRECTORY = "data/users";

    @Id
    private String postId;

    @Override
    Post withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return Post.builder()
                .postId(postId)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    @Override
    Post withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.remove(reactionUser);
        return Post.builder()
                .postId(postId)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    @Override
    String getImageDirectory() {
        return String.format("%s/%s", getFolderDirectory(), super.getImageName());
    }

    @Override
    String getFolderDirectory() {
        return String.format("%s/%s/%s", MAIN_DIRECTORY, super.getCreator().userId(), postId);
    }
}
