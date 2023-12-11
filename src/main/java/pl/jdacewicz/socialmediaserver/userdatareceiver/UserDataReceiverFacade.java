package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public UserDto getUserById(String id) {
        var foundUser = userDataReceiverService.getUserById(id);
        return userMapper.mapToDto(foundUser);
    }

    public UserDto getUserByEmail(String email) {
        var user = userDataReceiverService.getUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    public Page<UserDto> getUsersByFirstnamesAndLastnames(Set<String> firstnames, Set<String> lastnames, int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var users = userDataReceiverService.getUsersByFirstnamesAndLastnames(firstnames, lastnames, pageable);
        return users.map(userMapper::mapToDto);
    }

    @Transactional
    public UserDto createUser(MultipartFile profileImage, RegisterRequest registerRequest) throws IOException {
        User createdUser;
        if (profileImage != null && !profileImage.isEmpty()) {
            var newFileName = fileStorageFacade.generateFilename(profileImage)
                    .fileName();
            createdUser = userDataReceiverService.createUserWithProfilePicture(registerRequest, newFileName);
            fileStorageFacade.uploadImage(profileImage, new FileUploadRequest(newFileName, createdUser.getFolderDirectory()));

        } else {
            createdUser = userDataReceiverService.createUserWithoutProfilePicture(registerRequest);
        }
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
    void deleteAllUsersData() throws IOException {
        var directoryDeleteRequest = new DirectoryDeleteRequest(User.MAIN_DIRECTORY);
        userDataReceiverService.deleteAllUsers();
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }
}
