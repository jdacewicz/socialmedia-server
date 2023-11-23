package pl.jdacewicz.socialmediaserver.bangiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TemporaryBanGiverServiceTest {

    TemporaryBanGiverService temporaryBanGiverService;

    TemporaryBanRepositoryTest temporaryBanRepositoryTest;
    UserDataReceiverFacade userDataReceiverFacade;

    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        temporaryBanRepositoryTest = new TemporaryBanRepositoryTest();
        temporaryBanGiverService = new TemporaryBanGiverService(temporaryBanRepositoryTest, userDataReceiverFacade);
    }

    @Test
    void should_revoke_all_temporary_bans_when_revoking_all_temporary_bans_by_user_id() {
        //Given
        var userId = "id";
        var tempBan = TemporaryBan.builder()
                .bannedUser(new BannedUser(userId))
                .revoked(false)
                .build();
        temporaryBanRepositoryTest.save(tempBan);
        //When
        temporaryBanGiverService.revokeAllTemporaryBansByUserId(userId);
        var result = temporaryBanRepositoryTest.findAllByBannedUserAndRevoked(new BannedUser(userId), false);
        //Then
        assertTrue(result.isEmpty());

    }

    @Test
    void should_return_created_ban_when_creating_ban() {
        //Given
        var userId = "id";
        var bannedTo = LocalDateTime.of(2000, 1, 1, 1, 1);
        var userTemporaryBanRequest = new UserTemporaryBanRequest(bannedTo, "reason");
        var loggedUserDto = LoggedUserDto.builder()
                .userId("idTwo")
                .build();
        when(userDataReceiverFacade.getLoggedInUser()).thenReturn(loggedUserDto);
        //When
        var result = temporaryBanGiverService.createBan(userId, userTemporaryBanRequest);
        //Then
        assertEquals(bannedTo, result.getTo());
        assertEquals(userTemporaryBanRequest.reason(), result.getReason());
        assertEquals(userId, result.getBannedUser()
                .userId());
        assertEquals(loggedUserDto.getUserId(), result.getBlockingUser()
                .userId());
    }

    @Test
    void should_return_expired_bans_when_getting_new_expired_bans() {
        //Given
        var tempBan = TemporaryBan.builder()
                .expired(false)
                .revoked(false)
                .build();
        temporaryBanRepositoryTest.save(tempBan);
        try (MockedStatic<BanChecker> banFilter = Mockito.mockStatic(BanChecker.class)) {
            banFilter.when(() -> BanChecker.getNewExpiredBans(List.of(tempBan)))
                    .thenReturn(Set.of(tempBan));
            tempBan.setBanExpired();
            //When
            var result = temporaryBanGiverService.checkNewExpiredBans();
            //Then
            assertFalse(result.isEmpty());
            assertTrue(result.get(0)
                    .isExpired());
        }
    }
}