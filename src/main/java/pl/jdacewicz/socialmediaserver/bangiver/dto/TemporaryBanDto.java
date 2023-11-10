package pl.jdacewicz.socialmediaserver.bangiver.dto;

import lombok.Builder;

@Builder
public record TemporaryBanDto(String banId,
                              String bannedUserId) {
}
