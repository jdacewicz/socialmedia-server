package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportDto;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

import java.util.Set;

@RequiredArgsConstructor
public class ReportDataReceiverFacade {

    private final ReportDataService reportDataService;
    private final ReportMapper reportMapper;

    public Set<ReportDto> getReports(String reportedDataType) {
        var reports = reportDataService.getReportsByDataType(reportedDataType);
        return reportMapper.mapToDto(reports);
    }

    public void report(String reportedDataId, String authenticationHeader, ReportRequest reportRequest, String reportedDataType) {
        reportDataService.createReport(reportedDataId, authenticationHeader, reportRequest, reportedDataType);
    }

    public void deleteReport(String reportedDataId) {
        reportDataService.deleteReport(reportedDataId);
    }
}
