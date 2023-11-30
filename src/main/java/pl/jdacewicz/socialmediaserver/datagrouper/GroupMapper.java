package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupDto;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;

@Component
@RequiredArgsConstructor
class GroupMapper {

    private final FileMapperFacade fileMapperFacade;

    public GroupDto mapToDto(Group group) {
        var image = fileMapperFacade.mapToFile(new MapRequest(group.getImageName(), group.getFolderDirectory()));
        return GroupDto.builder()
                .groupId(group.getGroupId())
                .name(group.getName())
                .image(image)
                .build();
    }
}
