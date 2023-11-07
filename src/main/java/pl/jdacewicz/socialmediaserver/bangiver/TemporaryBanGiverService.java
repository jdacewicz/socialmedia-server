package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
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

    TemporaryBan createBan(String userId, UserTemporaryBanRequest userTemporaryBanRequest) {
        var tempBan = prepareBan(userId, userTemporaryBanRequest);
        return temporaryBanRepository.save(tempBan);
    }

    List<TemporaryBan> checkNewExpiredBans() {
        var tempBans = temporaryBanRepository.findAllByExpiredAndRevoked(false, false);
        var checkedBans = BanChecker.getNewExpiredBans(tempBans);
        return temporaryBanRepository.saveAll(checkedBans);
    }

    private TemporaryBan prepareBan(String userId, UserTemporaryBanRequest userTemporaryBanRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
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
