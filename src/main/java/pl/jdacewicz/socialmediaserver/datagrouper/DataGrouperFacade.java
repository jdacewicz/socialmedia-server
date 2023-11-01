package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupImage;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.PostGroupDto;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.PostGroupRequest;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;

import java.io.IOException;

@RequiredArgsConstructor
public class DataGrouperFacade {

    private final DataGrouperService dataGrouperService;
    private final FileStorageFacade fileStorageFacade;

    public PostGroupDto getPostGroupById(String groupId) {
        var foundGroup = dataGrouperService.getPostGroupById(groupId);
        return mapToDto(foundGroup);
    }

    @Transactional
    public PostGroupDto createPostGroup(MultipartFile groupImage, PostGroupRequest postGroupRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(groupImage)
                .fileName();
        var createdGroup = dataGrouperService.createPostGroup(postGroupRequest, newFileName);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdGroup.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(groupImage, imageUploadRequest);
        return mapToDto(createdGroup);

    }

    @Transactional
    public void deletePostGroup(String groupId) throws IOException {
        var foundGroup = dataGrouperService.getPostGroupById(groupId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundGroup.getFolderDirectory());
        dataGrouperService.deletePostGroup(foundGroup);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
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
