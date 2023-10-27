package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Builder
record Post(@Id
            String postId,

            String content,

            UserDto creator,

            String imageName,

            LocalDateTime creationDateTime,

            List<ReactionUser> reactionUsers) {

    private final static String MAIN_DIRECTORY = "data/users";

    static class PostBuilder {
        private List<ReactionUser> reactionUsers = new LinkedList<>();
    }

    Post withReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.reactionUsers());
        newReactionUsers.add(reactionUser);
        return Post.builder()
                .postId(postId)
                .content(content)
                .creator(creator)
                .imageName(imageName)
                .creationDateTime(creationDateTime)
                .reactionUsers(newReactionUsers)
                .build();
    }

    Post withoutReactionUser(ReactionUser reactionUser) {
        var newReactionUsers = new LinkedList<>(this.reactionUsers());
        newReactionUsers.remove(reactionUser);
        return Post.builder()
                .postId(postId)
                .content(content)
                .creator(creator)
                .imageName(imageName)
                .creationDateTime(creationDateTime)
                .reactionUsers(newReactionUsers)
                .build();
    }

    String getPostImageDirectory() {
        return String.format("%s/%s", getPostFolderDirectory(), imageName);
    }

    String getPostFolderDirectory() {
        return String.format("%s/%s/%s", MAIN_DIRECTORY, creator.userId(), postId);
    }

    boolean isReactionUserStored(ReactionUser reactionUser) {
        return reactionUsers().contains(reactionUser);
    }
}
