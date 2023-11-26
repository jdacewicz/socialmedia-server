package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.configuration.JwtClaimsExtractor;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.RegisterRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class UserDataReceiverFacade {

    private final UserDataReceiverService userDataReceiverService;
    private final UserMapper userMapper;
    private final FileStorageFacade fileStorageFacade;
    private final JwtClaimsExtractor jwtClaimsExtractor;

    public LoggedUserDto getLoggedInUser(String authenticationHeader) {
        var jwtToken = authenticationHeader.substring(7);
        var userName = jwtClaimsExtractor.extractUsername(jwtToken);
        var loggedUser = userDataReceiverService.getUserByEmail(userName);
        return userMapper.mapToLoggedDto(loggedUser);
    }

    public UserDto getUserByEmail(String email) {
        var user = userDataReceiverService.getUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    public Set<UserDto> getUsersByFirstnamesAndLastnames(Set<String> firstnames, Set<String> lastnames) {
        var users = userDataReceiverService.getUsersByFirstnamesAndLastnames(firstnames, lastnames);
        return userMapper.mapToDto(users);
    }

    @Transactional
    public UserDto createUser(MultipartFile profileImage, RegisterRequest registerRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(profileImage)
                .fileName();
        var createdUser = userDataReceiverService.createUser(registerRequest);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdUser.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(profileImage, imageUploadRequest);
        return userMapper.mapToDto(createdUser);
    }

    public void banUser(String userId, String banId) {
        userDataReceiverService.banUser(userId, banId);
    }

    public void unbanUser(String userId) {
        userDataReceiverService.unbanUser(userId);
    }

    public void unbanUsers(Set<String> userIds) {
        userDataReceiverService.unbanUsers(userIds);
    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    private void deleteAllUsersData() throws IOException {
        var directoryDeleteRequest = new DirectoryDeleteRequest(User.MAIN_DIRECTORY);
        userDataReceiverService.deleteAllUsers();
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }
}
