package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Service
@RequiredArgsConstructor
class BanGiverService {

    private final BanRepository banRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Transactional
    public void revokeAllBansByUserId(String userId) {
        var bannedUser = new BannedUser(userId);
        var revokedBans = banRepository.findAllByBannedUserAndRevoked(bannedUser, false)
                .stream()
                .peek(Ban::revokeBan)
                .toList();
        banRepository.saveAll(revokedBans);
    }

    boolean isPermanentBanAssignedToUser(String userId) {
        var bannedUser = new BannedUser(userId);
        return banRepository.existsByBannedUserAndRevoked(bannedUser ,false);
    }

    Ban createBan(String userId, String authenticationHeader, UserPermanentBanRequest userPermanentBanRequest) {
        var ban = prepareBan(userId, authenticationHeader, userPermanentBanRequest);
        return banRepository.save(ban);
    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllBans() {
        banRepository.deleteAll();
    }

    private Ban prepareBan(String userId, String authenticationHeader, UserPermanentBanRequest userPermanentBanRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser(authenticationHeader)
                .getUserId();
        var blockingUser = new BlockingUser(loggedUserId);
        var bannedUser = new BannedUser(userId);
        return Ban.builder()
                .reason(userPermanentBanRequest.reason())
                .bannedUser(bannedUser)
                .blockingUser(blockingUser)
                .build();
    }
}
