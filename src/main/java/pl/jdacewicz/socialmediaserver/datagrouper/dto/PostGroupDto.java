package pl.jdacewicz.socialmediaserver.datagrouper.dto;

import lombok.Builder;

@Builder
public record PostGroupDto(String groupId,
                           String name,
                           GroupImage groupImage) {
}
