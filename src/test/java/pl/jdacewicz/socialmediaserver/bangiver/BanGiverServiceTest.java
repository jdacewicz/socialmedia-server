package pl.jdacewicz.socialmediaserver.bangiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class BanGiverServiceTest {

    BanGiverService banGiverService;

    BanRepositoryTest banRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;

    @BeforeEach
    void setUp() {
        banRepositoryTest = new BanRepositoryTest();
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        banGiverService = new BanGiverService(banRepositoryTest, userDataReceiverFacade);
    }

    @Test
    void should_revoke_all_bans_when_revoking_all_bans_by_user_id() {
        //Given
        var userId = "id";
        var ban = Ban.builder()
                .bannedUser(new BannedUser(userId))
                .revoked(false)
                .build();
        banRepositoryTest.save(ban);
        //When
        banGiverService.revokeAllBansByUserId(userId);
        var result = banRepositoryTest.findAllNotRevokedByBannedUserId(userId);
        //Then
        assertTrue(result.isEmpty());
    }

    @Test
    void should_return_created_ban_when_creating_ban() {
        //Given
        var bannedUserId = "id";
        var userPermanentBanRequest = new UserPermanentBanRequest("reason");
        var loggedUserDto = UserDto.builder()
                .userId("idTwo")
                .build();
        when(userDataReceiverFacade.getLoggedInUser()).thenReturn(loggedUserDto);
        //When
        var result = banGiverService.createBan(bannedUserId, userPermanentBanRequest);
        //Then
        assertEquals(bannedUserId, result.getBannedUser()
                .userId());
        assertEquals(userPermanentBanRequest.reason(), result.getReason());
        assertEquals(loggedUserDto.userId(), result.getBlockingUser()
                .userId());
    }
}