package pl.jdacewicz.socialmediaserver.bangiver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    private BannedUser bannedUser;

    @NotNull
    private BlockingUser blockingUser;

    @Builder.Default
    private LocalDateTime from = LocalDateTime.now();

    @NotBlank
    @Size(max = 255)
    private String reason;

    @Builder.Default
    private boolean revoked = false;

    void revokeBan() {
        this.revoked = true;
    }
}
