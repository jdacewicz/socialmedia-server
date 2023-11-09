package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bangiver.dto.PermanentBanResponse;
import pl.jdacewicz.socialmediaserver.bangiver.dto.TemporaryBanResponse;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BanGiverFacade {

    private final BanGiverService banGiverService;
    private final TemporaryBanGiverService temporaryBanGiverService;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Transactional
    public PermanentBanResponse banUserPermanently(String userId, UserPermanentBanRequest userPermanentBanRequest) {
        var createdBan = banGiverService.createBan(userId, userPermanentBanRequest);
        temporaryBanGiverService.revokeAllTemporaryBansByUserId(userId);
        userDataReceiverFacade.banUser(userId, createdBan.getBanId());
        return mapToPermanentBanResponse(createdBan);
    }

    @Transactional
    public TemporaryBanResponse banUserTemporary(String userId, UserTemporaryBanRequest userTemporaryBanRequest) {
        if (banGiverService.isPermanentBanAssignedToUser(userId)) {
            throw new UnsupportedOperationException();
        }
        var createdTempBan = temporaryBanGiverService.createBan(userId, userTemporaryBanRequest);
        userDataReceiverFacade.banUser(userId, createdTempBan.getBanId());
        return mapToTemporaryBanResponse(createdTempBan);
    }

    @Transactional
    public void revokeBansByUserId(String userId) {
        banGiverService.revokeAllBansByUserId(userId);
        userDataReceiverFacade.unbanUser(userId);
    }

    @Scheduled(cron = "${application.scheduled-tasks.checking-temporary-bans-expiration.cron}")
    @Transactional
    public void checkAndUnbanUsersWithExpiredBans() {
        var expiredBanUserIds = temporaryBanGiverService.checkNewExpiredBans()
                .stream()
                .map(expiredBan -> expiredBan.getBannedUser()
                        .userId())
                .collect(Collectors.toSet());
        userDataReceiverFacade.unbanUsers(expiredBanUserIds);
    }

    private PermanentBanResponse mapToPermanentBanResponse(Ban ban) {
        return PermanentBanResponse.builder()
                .banId(ban.getBanId())
                .type(BanType.PERMANENT.name())
                .from(ban.getFrom())
                .reason(ban.getReason())
                .build();
    }

    private TemporaryBanResponse mapToTemporaryBanResponse(TemporaryBan temporaryBan) {
        return TemporaryBanResponse.builder()
                .banId(temporaryBan.getBanId())
                .type(BanType.TEMPORARY.name())
                .from(temporaryBan.getFrom())
                .to(temporaryBan.getTo())
                .reason(temporaryBan.getReason())
                .build();
    }
}
