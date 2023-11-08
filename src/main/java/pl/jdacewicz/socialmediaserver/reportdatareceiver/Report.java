package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@AllArgsConstructor
class Report {

    private String reportingUserId;

    private ReportType type;

    private String content;

    @Builder.Default
    private LocalDateTime reportDateTime = LocalDateTime.now();
}
