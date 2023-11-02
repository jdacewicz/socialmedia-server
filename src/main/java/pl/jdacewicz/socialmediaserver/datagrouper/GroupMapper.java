package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupDto;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupImage;

@Component
class GroupMapper {

    public GroupDto mapToDto(Group group) {
        var groupImage = new GroupImage(group.getImageName(), group.getFolderDirectory());
        return GroupDto.builder()
                .groupId(group.getGroupId())
                .name(group.getName())
                .groupImage(groupImage)
                .build();
    }
}
