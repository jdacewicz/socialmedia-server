package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportDto;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

@RequiredArgsConstructor
public class ReportDataReceiverFacade {

    private final ReportDataService reportDataService;
    private final ReportMapper reportMapper;

    public Page<ReportDto> getReports(String reportedDataType, int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var reports = reportDataService.getReportsByDataType(reportedDataType, pageable);
        return reports.map(reportMapper::mapToDto);
    }

    public void report(String reportedDataId, String authenticationHeader, ReportRequest reportRequest, String reportedDataType) {
        reportDataService.createReport(reportedDataId, authenticationHeader, reportRequest, reportedDataType);
    }

    public void deleteReport(String reportedDataId) {
        reportDataService.deleteReport(reportedDataId);
    }
}
