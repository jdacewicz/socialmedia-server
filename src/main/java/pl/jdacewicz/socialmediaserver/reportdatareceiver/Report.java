package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reports")
@Getter
@Builder
@AllArgsConstructor
class Report {

    @Id
    private String reportId;

    private ReportType reportType;

    private String reportedDataId;

    private DataType dataType;

    private String reportingUserId;

    private String content;

    @Builder.Default
    private LocalDateTime reportDateTime = LocalDateTime.now();
}
