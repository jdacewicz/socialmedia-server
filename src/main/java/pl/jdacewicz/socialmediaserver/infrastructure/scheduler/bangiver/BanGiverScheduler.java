package pl.jdacewicz.socialmediaserver.infrastructure.scheduler.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bangiver.BanGiverFacade;
import pl.jdacewicz.socialmediaserver.bangiver.dto.TemporaryBanDto;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BanGiverScheduler {

    private final BanGiverFacade banGiverFacade;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Scheduled(cron = "${application.scheduled-tasks.checking-temporary-bans-expiration.cron}")
    @Transactional
    public void checkAndUnbanUsersWithExpiredBans() {
        var expiredBanUserIds = banGiverFacade.checkNewExpiredBans()
                .stream()
                .map(TemporaryBanDto::bannedUserId)
                .collect(Collectors.toSet());
        userDataReceiverFacade.unbanUsers(expiredBanUserIds);
    }
}
