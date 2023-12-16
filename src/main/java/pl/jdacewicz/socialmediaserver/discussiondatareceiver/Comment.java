package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.LinkedList;

@Document(collection = "comments")
@Getter
@SuperBuilder
@NoArgsConstructor
class Comment extends Discussion<Comment> {

    @Override
    Comment withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return Comment.builder()
                .discussionId(getDiscussionId())
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getFullImageDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    @Override
    Comment withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.remove(reactionUser);
        return Comment.builder()
                .discussionId(getDiscussionId())
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getFullImageDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }
}
