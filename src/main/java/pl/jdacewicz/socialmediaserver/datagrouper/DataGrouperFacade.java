package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupDto;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupRequest;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;

import java.io.IOException;

@RequiredArgsConstructor
public class DataGrouperFacade {

    private final FileStorageFacade fileStorageFacade;
    private final DataGrouperServiceFactory dataGrouperServiceFactory;
    private final GroupMapper groupMapper;

    public GroupDto getGroupById(String groupId, String type) {
        var dataGrouperService = dataGrouperServiceFactory.getDataGrouperService(type);
        var foundGroup = dataGrouperService.getGroupById(groupId);
        return groupMapper.mapToDto(foundGroup);
    }


    @Transactional
    public GroupDto createGroup(MultipartFile groupImage, String authenticationHeader, GroupRequest groupRequest, String type) throws IOException {
        var dataGrouperService = dataGrouperServiceFactory.getDataGrouperService(type);
        var newFileName = fileStorageFacade.generateFilename(groupImage)
                .fileName();
        var createdGroup = dataGrouperService.createGroup(authenticationHeader, groupRequest, newFileName);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdGroup.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(groupImage, imageUploadRequest);
        return groupMapper.mapToDto(createdGroup);
    }

    @Transactional
    public void deleteGroupById(String groupId, String type) throws IOException {
        var dataGrouperService = dataGrouperServiceFactory.getDataGrouperService(type);
        var foundGroup = dataGrouperService.getGroupById(groupId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundGroup.getFolderDirectory());
        dataGrouperService.deleteGroupById(groupId);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    private void deleteGroupsData() throws IOException {
        var directoryDeleteRequest = new DirectoryDeleteRequest(Group.MAIN_DIRECTORY);
        var types = GroupType.values();
        for (GroupType type: types) {
            var dataGrouperService = dataGrouperServiceFactory.getDataGrouperService(type.name());
            dataGrouperService.deleteAllGroups();
        }
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }
}
