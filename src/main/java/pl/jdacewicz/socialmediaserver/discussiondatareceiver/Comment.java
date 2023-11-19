package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.LinkedList;

@Document(collection = "comments")
@Getter
@SuperBuilder
@NoArgsConstructor
class Comment extends Discussion<Comment> {

    @Id
    private String commentId;

    @Override
    Comment withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return Comment.builder()
                .commentId(commentId)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    @Override
    Comment withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.remove(reactionUser);
        return Comment.builder()
                .commentId(commentId)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    @Override
    String getFolderDirectory() {
        return String.format("%s/%s", getImageMainDirectory(), commentId);
    }
}
