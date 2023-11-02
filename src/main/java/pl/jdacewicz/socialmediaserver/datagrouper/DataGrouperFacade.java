package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
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
    public GroupDto createGroup(MultipartFile groupImage, GroupRequest groupRequest, String type) throws IOException {
        var dataGrouperService = dataGrouperServiceFactory.getDataGrouperService(type);
        var newFileName = fileStorageFacade.generateFilename(groupImage)
                .fileName();
        var createdGroup = dataGrouperService.createGroup(groupRequest, newFileName);
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
}
