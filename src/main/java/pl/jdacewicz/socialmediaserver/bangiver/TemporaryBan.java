package pl.jdacewicz.socialmediaserver.bangiver;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bans")
@Getter
@SuperBuilder
class TemporaryBan extends Ban {

    private LocalDateTime to;

    @Builder.Default
    private boolean expired = false;
}
