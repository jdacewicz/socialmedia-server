package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bangiver.dto.*;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@RequiredArgsConstructor
public class BanGiverFacade {

    private final BanGiverService banGiverService;
    private final TemporaryBanGiverService temporaryBanGiverService;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BanMapper banMapper;
    private final TemporaryBanMapper temporaryBanMapper;

    @Transactional
    public PermanentBanResponse banUserPermanently(String userId, UserPermanentBanRequest userPermanentBanRequest) {
        var createdBan = banGiverService.createBan(userId, userPermanentBanRequest);
        temporaryBanGiverService.revokeAllTemporaryBansByUserId(userId);
        userDataReceiverFacade.banUser(userId, createdBan.getBanId());
        return banMapper.mapToPermanentBanResponse(createdBan);
    }

    @Transactional
    public TemporaryBanResponse banUserTemporary(String userId, UserTemporaryBanRequest userTemporaryBanRequest) {
        if (banGiverService.isPermanentBanAssignedToUser(userId)) {
            throw new UnsupportedOperationException();
        }
        var createdTempBan = temporaryBanGiverService.createBan(userId, userTemporaryBanRequest);
        userDataReceiverFacade.banUser(userId, createdTempBan.getBanId());
        return temporaryBanMapper.mapToTemporaryBanResponse(createdTempBan);
    }

    @Transactional
    public void revokeBansByUserId(String userId) {
        banGiverService.revokeAllBansByUserId(userId);
        userDataReceiverFacade.unbanUser(userId);
    }

    public List<TemporaryBanDto> checkNewExpiredBans() {
        var expiredTempBans = temporaryBanGiverService.checkNewExpiredBans();
        return temporaryBanMapper.mapToDto(expiredTempBans);
    }
}
