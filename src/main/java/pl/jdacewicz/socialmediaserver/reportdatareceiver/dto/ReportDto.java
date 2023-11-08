package pl.jdacewicz.socialmediaserver.reportdatareceiver.dto;

import lombok.Builder;

@Builder
public record ReportDto(String reportId,
                        String reportType,
                        String reportDataType,
                        String reportedDataId,
                        String content) {
}
