package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.LinkedList;

@Document(collection = "comments_grouped")
@Getter
@SuperBuilder
@NoArgsConstructor
class GroupedComment extends Comment {

    @NotBlank
    private String groupId;

    @Override
    GroupedComment withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return GroupedComment.builder()
                .groupId(groupId)
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
    GroupedComment withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.remove(reactionUser);
        return GroupedComment.builder()
                .groupId(groupId)
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
