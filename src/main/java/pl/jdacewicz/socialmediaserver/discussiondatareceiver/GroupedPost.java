package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.HashSet;
import java.util.LinkedList;

@Document(collection = "grouped_posts")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
class GroupedPost extends Post {

    @NotBlank
    private String groupId;

    @Override
    GroupedPost withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return GroupedPost.builder()
                .groupId(groupId)
                .comments(getComments())
                .discussionId(getDiscussionId())
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageMainDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    @Override
    GroupedPost withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.remove(reactionUser);
        return GroupedPost.builder()
                .groupId(groupId)
                .comments(getComments())
                .discussionId(getDiscussionId())
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageMainDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    GroupedPost withComment(Comment comment) {
        var newComments = new HashSet<>(getComments());
        newComments.add(comment);
        return GroupedPost.builder()
                .groupId(groupId)
                .comments(newComments)
                .discussionId(getDiscussionId())
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageMainDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(getReactionUsers())
                .build();
    }
}
