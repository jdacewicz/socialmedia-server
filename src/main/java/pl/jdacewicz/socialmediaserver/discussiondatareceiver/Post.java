package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
abstract class Post extends Discussion<Post> {

    @DBRef(lazy = true)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    abstract Post withComment(Comment comment);
}
