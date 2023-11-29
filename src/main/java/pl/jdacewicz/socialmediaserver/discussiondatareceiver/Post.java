package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Document(collection = "posts")
@Getter
@SuperBuilder
@NoArgsConstructor
class Post extends Discussion<Post> {

    @Id
    private String postId;

    @DBRef(lazy = true)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    @Override
    Post withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.getReactionUsers());
        newReactionUsers.add(reactionUser);
        return Post.builder()
                .postId(postId)
                .comments(comments)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageMainDirectory())
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
                .comments(comments)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageMainDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(newReactionUsers)
                .build();
    }

    Post withComment(Comment comment) {
        var newComments = new HashSet<>(this.comments);
        newComments.add(comment);
        return Post.builder()
                .postId(postId)
                .comments(newComments)
                .content(getContent())
                .creator(getCreator())
                .imageName(getImageName())
                .imageMainDirectory(getImageMainDirectory())
                .creationDateTime(getCreationDateTime())
                .reactionUsers(getReactionUsers())
                .build();
    }

    @Override
    String getFolderDirectory() {
        return String.format("%s/%s", getImageMainDirectory(), postId);
    }
}
