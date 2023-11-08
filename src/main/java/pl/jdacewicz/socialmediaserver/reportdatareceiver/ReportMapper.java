package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.springframework.stereotype.Component;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class ReportMapper {

    Set<ReportDto> mapToDto(List<Report> reports) {
        return reports.stream()
                .map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    ReportDto mapToDto(Report report) {
        return ReportDto.builder()
                .reportId(report.getReportId())
                .reportType(report.getReportType()
                        .name())
                .reportDataType(report.getDataType()
                        .name())
                .reportedDataId(report.getReportedDataId())
                .content(report.getContent())
                .build();
    }
}
