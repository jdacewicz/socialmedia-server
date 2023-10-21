package pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto;

import lombok.Builder;

@Builder
public record PostDto(String postId,
                      String content) {
}
