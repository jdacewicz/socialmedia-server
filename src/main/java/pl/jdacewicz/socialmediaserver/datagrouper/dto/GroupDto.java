package pl.jdacewicz.socialmediaserver.datagrouper.dto;

import lombok.Builder;

@Builder
public record GroupDto(String groupId,
                       String name,
                       GroupImage groupImage) {
}
