package pl.jdacewicz.socialmediaserver.bangiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class BanChecker {

    static Set<TemporaryBan> getNewExpiredBans(List<TemporaryBan> tempBans) {
        var now = LocalDateTime.now();
        return tempBans.stream()
                .filter(tempBan -> tempBan.getTo()
                        .isBefore(now))
                .peek(TemporaryBan::setBanExpired)
                .collect(Collectors.toSet());
    }
}
