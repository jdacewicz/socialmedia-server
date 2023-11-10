package pl.jdacewicz.socialmediaserver.reportdatareceiver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ReportRequest(@NotBlank
                            @Size(max = 32)
                            String reportType,

                            @NotEmpty
                            @Size(max = 255)
                            String content) {
}
