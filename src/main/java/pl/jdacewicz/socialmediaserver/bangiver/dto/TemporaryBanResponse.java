package pl.jdacewicz.socialmediaserver.bangiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TemporaryBanResponse(String banId,
                                   String type,
                                   LocalDateTime from,
                                   LocalDateTime to,
                                   String reason) {
}
