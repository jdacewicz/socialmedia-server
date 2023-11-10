package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.bangiver.dto.PermanentBanResponse;

@Component
class BanMapper {

    PermanentBanResponse mapToPermanentBanResponse(Ban ban) {
        return PermanentBanResponse.builder()
                .banId(ban.getBanId())
                .type(BanType.PERMANENT.name())
                .from(ban.getFrom())
                .reason(ban.getReason())
                .build();
    }
}
