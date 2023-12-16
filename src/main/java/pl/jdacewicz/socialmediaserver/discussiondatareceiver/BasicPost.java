package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.HashSet;
import java.util.LinkedList;

@Document(collection = "posts")
@Getter
@SuperBuilder
@NoArgsConstructor
class BasicPost extends Post {

    @Override
    BasicPost withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return BasicPost.builder()
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
    BasicPost withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.remove(reactionUser);
        return BasicPost.builder()
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

    BasicPost withComment(Comment comment) {
        var newComments = new HashSet<>(getComments());
        newComments.add(comment);
        return BasicPost.builder()
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
