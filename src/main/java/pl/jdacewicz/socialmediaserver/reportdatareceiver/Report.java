package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reports")
@Builder
record Report(@Id
              String reportId,

              @NotNull
              ReportType reportType,

              @NotBlank
              String reportedDataId,

              @NotNull
              DataType dataType,

              @NotBlank
              String reportingUserId,

              @NotEmpty
              @Size(max = 255)
              String content,

              LocalDateTime reportDateTime) {

    @SuppressWarnings("unused")
    static class ReportBuilder {
        private LocalDateTime reportDateTime = LocalDateTime.now();
    }
}
