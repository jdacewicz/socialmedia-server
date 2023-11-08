package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
@Getter
@SuperBuilder
class UserReport extends Report {

    @Id
    private String reportId;

    private String userId;
}
