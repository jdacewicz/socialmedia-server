package pl.jdacewicz.socialmediaserver.bangiver;

import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.bangiver.dto.TemporaryBanDto;
import pl.jdacewicz.socialmediaserver.bangiver.dto.TemporaryBanResponse;

import java.util.List;

@Component
class TemporaryBanMapper {

    List<TemporaryBanDto> mapToDto(List<TemporaryBan> temporaryBans) {
        return temporaryBans.stream()
                .map(this::mapToDto)
                .toList();
    }

    TemporaryBanDto mapToDto(TemporaryBan temporaryBan) {
        return TemporaryBanDto.builder()
                .banId(temporaryBan.getBanId())
                .bannedUserId(temporaryBan.getBannedUser()
                        .userId())
                .build();
    }

    TemporaryBanResponse mapToTemporaryBanResponse(TemporaryBan temporaryBan) {
        return TemporaryBanResponse.builder()
                .banId(temporaryBan.getBanId())
                .type(BanType.TEMPORARY.name())
                .from(temporaryBan.getFrom())
                .to(temporaryBan.getTo())
                .reason(temporaryBan.getReason())
                .build();
    }
}
