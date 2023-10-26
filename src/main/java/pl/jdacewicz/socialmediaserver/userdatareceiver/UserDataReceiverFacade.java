package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.io.IOException;

@RequiredArgsConstructor
public class UserDataReceiverFacade {

    private final UserDataReceiverService userDataReceiverService;
    private final FileStorageFacade fileStorageFacade;

    public UserDto getLoggedInUser() {
        var loggedUser = userDataReceiverService.getLoggedInUser();
        return mapToDto(loggedUser);
    }

    public UserDto getUserByEmail(String email) {
        var user = userDataReceiverService.getUserByEmail(email);
        return mapToDto(user);
    }

    @Transactional
    public UserDto createUser(MultipartFile profileImage, RegisterRequest registerRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(profileImage)
                .fileName();
        var createdUser = userDataReceiverService.createUser(registerRequest);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdUser.getUserFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(profileImage, imageUploadRequest);
        return mapToDto(createdUser);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .userId(user.userId())
                .firstname(user.firstname())
                .firstname(user.lastname())
                .profilePictureUrl(user.getUserImageDirectory())
                .build();
    }
}
