package pl.jdacewicz.socialmediaserver.bangiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BanGiverFacadeTest {

    BanGiverFacade banGiverFacade;

    BanGiverService banGiverService;
    TemporaryBanGiverService temporaryBanGiverService;
    UserDataReceiverFacade userDataReceiverFacade;
    BanMapper banMapper;
    TemporaryBanMapper temporaryBanMapper;

    @BeforeEach
    void setUp() {
        banGiverService = Mockito.mock(BanGiverService.class);
        temporaryBanGiverService = Mockito.mock(TemporaryBanGiverService.class);
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        banMapper = Mockito.mock(BanMapper.class);
        temporaryBanMapper = Mockito.mock(TemporaryBanMapper.class);
        banGiverFacade = new BanGiverFacade(banGiverService, temporaryBanGiverService, userDataReceiverFacade,
                banMapper, temporaryBanMapper);
    }

    @Test
    void should_not_throw_exception_when_banning_user_temporary_by_id_of_user_not_banned() {
        //Given
        var userId = "id";
        var userTemporaryBanRequest = new UserTemporaryBanRequest(LocalDateTime.now(), "reason");
        var tempBan = TemporaryBan.builder()
                .banId("id")
                .from(LocalDateTime.now())
                .to(userTemporaryBanRequest.to())
                .reason(userTemporaryBanRequest.reason())
                .build();
        var authenticationHeader = "token";
        when(banGiverService.isBanAssignedToUser(userId)).thenReturn(false);
        when(temporaryBanGiverService.createBan(userId, authenticationHeader, userTemporaryBanRequest)).thenReturn(tempBan);
        //When
        //Then
        assertDoesNotThrow(() -> banGiverFacade.banUserTemporary(userId, authenticationHeader, userTemporaryBanRequest));
    }

    @Test
    void should_throw_unsupported_operation_exception_when_banning_user_temporary_by_id_of_banned_user() {
        //Given
        var userId = "id";
        var userTemporaryBanRequest = new UserTemporaryBanRequest(LocalDateTime.now(), "reason");
        var authenticationHeader = "token";
        when(banGiverService.isBanAssignedToUser(userId)).thenReturn(true);
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> banGiverFacade.banUserTemporary(userId, authenticationHeader, userTemporaryBanRequest));
    }

    @Test
    void should_not_throw_exception_when_banning_user_permanently_by_id_of_user_not_banned() {
        //Given
        var userId = "id";
        var userPermanentBanRequest = new UserPermanentBanRequest("reason");
        var permBan = Ban.builder()
                .banId("id")
                .from(LocalDateTime.now())
                .reason(userPermanentBanRequest.reason())
                .build();
        var authenticationHeader = "token";
        when(banGiverService.isBanAssignedToUser(userId)).thenReturn(false);
        when(banGiverService.createBan(userId, authenticationHeader, userPermanentBanRequest)).thenReturn(permBan);
        //When
        //Then
        assertDoesNotThrow(() -> banGiverFacade.banUserPermanently(userId, authenticationHeader, userPermanentBanRequest));
    }

    @Test
    void should_throw_unsupported_operation_exception_when_banning_user_permanently_by_id_of_banned_user() {
        //Given
        var userId = "id";
        var userPermanentBanRequest = new UserPermanentBanRequest("reason");
        var authenticationHeader = "token";
        when(banGiverService.isBanAssignedToUser(userId)).thenReturn(true);
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> banGiverFacade.banUserPermanently(userId, authenticationHeader, userPermanentBanRequest));
    }
}