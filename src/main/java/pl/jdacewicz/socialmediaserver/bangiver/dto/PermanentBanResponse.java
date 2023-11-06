package pl.jdacewicz.socialmediaserver.bangiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PermanentBanResponse(String banId,
                                   String type,
                                   LocalDateTime from,
                                   String reason) {
}
