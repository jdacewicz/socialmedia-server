package pl.jdacewicz.socialmediaserver.datagrouper.dto;

import lombok.Builder;
import pl.jdacewicz.socialmediaserver.filemapper.dto.File;

@Builder
public record GroupDto(String groupId,
                       String name,
                       File image) {
}
