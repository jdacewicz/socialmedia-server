package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class TemporaryBanGiverService {

    private final TemporaryBanRepository temporaryBanRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Transactional
    public void revokeAllTemporaryBansByUserId(String userId) {
        var bannedUser = new BannedUser(userId);
        var revokedTempBans = temporaryBanRepository.findAllByBannedUserAndRevoked(bannedUser, false)
                .stream()
                .peek(TemporaryBan::revokeBan)
                .toList();
        temporaryBanRepository.saveAll(revokedTempBans);
    }

    TemporaryBan createBan(String userId, String authenticationHeader, UserTemporaryBanRequest userTemporaryBanRequest) {
        var tempBan = prepareBan(userId, authenticationHeader, userTemporaryBanRequest);
        return temporaryBanRepository.save(tempBan);
    }

    List<TemporaryBan> checkNewExpiredBans() {
        var tempBans = temporaryBanRepository.findAllByExpiredAndRevoked(false, false);
        var checkedBans = BanChecker.getNewExpiredBans(tempBans);
        return temporaryBanRepository.saveAll(checkedBans);
    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllBans() {
        temporaryBanRepository.deleteAll();
    }

    private TemporaryBan prepareBan(String userId, String jwtToken, UserTemporaryBanRequest userTemporaryBanRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser(jwtToken)
                .getUserId();
        var blockingUser = new BlockingUser(loggedUserId);
        var bannedUser = new BannedUser(userId);
        return TemporaryBan.builder()
                .to(userTemporaryBanRequest.to())
                .reason(userTemporaryBanRequest.reason())
                .bannedUser(bannedUser)
                .blockingUser(blockingUser)
                .build();
    }
}
