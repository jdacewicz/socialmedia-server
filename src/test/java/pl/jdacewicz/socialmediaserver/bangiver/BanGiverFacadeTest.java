package pl.jdacewicz.socialmediaserver.bangiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
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
    void should_not_throw_exception_when_banning_user_temporary_by_id_of_user_not_permanently_banned() {
        //Given
        var userId = "id";
        var userTemporaryBanRequest = new UserTemporaryBanRequest(LocalDateTime.now(), "reason");
        var tempBan = TemporaryBan.builder()
                .banId("id")
                .from(LocalDateTime.now())
                .to(userTemporaryBanRequest.to())
                .reason(userTemporaryBanRequest.reason())
                .build();
        when(banGiverService.isPermanentBanAssignedToUser(userId)).thenReturn(false);
        when(temporaryBanGiverService.createBan(userId, userTemporaryBanRequest)).thenReturn(tempBan);
        //When
        //Then
        assertDoesNotThrow(() -> banGiverFacade.banUserTemporary(userId, userTemporaryBanRequest));
    }

    @Test
    void should_throw_unsupported_operation_exception_when_banning_user_temporary_by_id_of_permanently_banned_user() {
        //Given
        var userId = "id";
        var userTemporaryBanRequest = new UserTemporaryBanRequest(LocalDateTime.now(), "reason");
        when(banGiverService.isPermanentBanAssignedToUser(userId)).thenReturn(true);
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> banGiverFacade.banUserTemporary(userId, userTemporaryBanRequest));
    }
}