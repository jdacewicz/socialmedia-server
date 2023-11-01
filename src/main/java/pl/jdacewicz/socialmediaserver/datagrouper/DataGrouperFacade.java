package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupImage;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.PostGroupDto;

@RequiredArgsConstructor
public class DataGrouperFacade {

    private final DataGrouperService dataGrouperService;

    public PostGroupDto getPostGroupById(String groupId) {
        var foundGroup = dataGrouperService.getPostGroupById(groupId);
        return mapToDto(foundGroup);
    }

    private PostGroupDto mapToDto(PostGroup postGroup) {
        var groupImage = new GroupImage(postGroup.getImageName(), postGroup.getFolderDirectory());
        return PostGroupDto.builder()
                .groupId(postGroup.getGroupId())
                .name(postGroup.getName())
                .groupImage(groupImage)
                .build();
    }
}
