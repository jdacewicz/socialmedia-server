package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.jdacewicz.socialmediaserver.bangiver.dto.BannedUser;

import java.time.LocalDateTime;

@Document(collection = "bans")
@Getter
@SuperBuilder
@AllArgsConstructor
class Ban {

    @Id
    private String banId;

    private BannedUser bannedUser;

    private BlockingUser blockingUser;

    @Builder.Default
    private LocalDateTime from = LocalDateTime.now();

    private String reason;

    @Builder.Default
    private boolean revoked = false;

    void revokeBan() {
        this.revoked = true;
    }
}
