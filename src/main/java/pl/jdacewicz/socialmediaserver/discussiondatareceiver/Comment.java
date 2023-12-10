package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
abstract class Comment extends Discussion<Comment> {
}
