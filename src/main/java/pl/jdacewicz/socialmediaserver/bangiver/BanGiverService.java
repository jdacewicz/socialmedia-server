package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BlockingUser;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;

@Service
@RequiredArgsConstructor
class BanGiverService {

    private final BanRepository banRepository;

    Ban createBan(String userId, UserPermanentBanRequest userPermanentBanRequest) {
        var blocker = new BlockingUser(userId);
        var ban = Ban.builder()
                .reason(userPermanentBanRequest.reason())
                .blockingUser(blocker)
                .build();
        return banRepository.save(ban);
    }

    @Transient
    void revokeBan(String banId) {
        banRepository.updateByBanId(banId, true);
    }
}
