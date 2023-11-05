package pl.jdacewicz.socialmediaserver.bangiver.dto;

import java.time.LocalDateTime;

public record UserTemporaryBanRequest(LocalDateTime to,
                                      String reason) {
}
