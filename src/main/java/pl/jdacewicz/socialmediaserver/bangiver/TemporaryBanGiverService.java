package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BlockingUser;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;

@Service
@RequiredArgsConstructor
class TemporaryBanGiverService {

    private final TemporaryBanRepository temporaryBanRepository;

    TemporaryBan createBan(String userId, UserTemporaryBanRequest userTemporaryBanRequest) {
        var blocker = new BlockingUser(userId);
        var tempBan = TemporaryBan.builder()
                .to(userTemporaryBanRequest.to())
                .reason(userTemporaryBanRequest.reason())
                .blockingUser(blocker)
                .build();
        return temporaryBanRepository.save(tempBan);
    }

    void setBanExpired(String banId) {
        temporaryBanRepository.updateByBanId(banId, true);
    }
}
