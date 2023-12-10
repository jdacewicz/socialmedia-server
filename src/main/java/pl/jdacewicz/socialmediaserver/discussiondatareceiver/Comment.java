package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
abstract class Comment extends Discussion<Comment> {
}
