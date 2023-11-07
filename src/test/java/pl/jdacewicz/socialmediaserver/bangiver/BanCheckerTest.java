package pl.jdacewicz.socialmediaserver.bangiver;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BanCheckerTest {

    @Test
    void should_return_expired_bans_when_getting_new_expired_bans_by_bans_datetime_expired() {
        //Given
        var expiredTempBan = TemporaryBan.builder()
                .to(LocalDateTime.now().minusHours(1))
                .build();
        var expiredTempBanTwo = TemporaryBan.builder()
                .to(LocalDateTime.now().minusHours(1))
                .build();
        var tempBans = List.of(expiredTempBan, expiredTempBanTwo);
        //When
        var result = BanChecker.getNewExpiredBans(tempBans);
        //Then
        assertEquals(2, result.size());
        assertTrue(result.stream()
                .allMatch(TemporaryBan::isExpired));
    }

    @Test
    void should_return_empty_bans_when_getting_new_expired_bans_by_bans_datetime_not_expired() {
        //Given
        var notExpiredTempBan = TemporaryBan.builder()
                .to(LocalDateTime.now().plusHours(1))
                .build();
        var notExpiredTempBanTwo = TemporaryBan.builder()
                .to(LocalDateTime.now().plusHours(2))
                .build();
        var tempBans = List.of(notExpiredTempBan, notExpiredTempBanTwo);
        //When
        var result = BanChecker.getNewExpiredBans(tempBans);
        //Then
        assertTrue((result.isEmpty()));
    }

}