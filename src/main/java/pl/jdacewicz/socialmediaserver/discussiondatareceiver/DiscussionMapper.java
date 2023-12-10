package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import java.util.Set;

interface DiscussionMapper<T extends Discussion<T>, S> {

    Set<S> mapToDto(Set<T> discussions);

    S mapToDto(T discussion);
}
