package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class ReportDataService {

    private final ReportRepository reportRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    List<Report> getReportsByDataType(String reportDataType) {
        var dataType = DataType.getType(reportDataType);
        return reportRepository.findAllByDataType(dataType);
    }

    void createReport(String reportedDataId, ReportRequest reportRequest, String reportDataType) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
        var reportType = ReportType.valueOf(reportRequest.reportType());
        var dataType = DataType.getType(reportDataType);
        var report = Report.builder()
                .reportType(reportType)
                .reportedDataId(reportedDataId)
                .dataType(dataType)
                .reportingUserId(loggedUserId)
                .content(reportRequest.content())
                .build();
        reportRepository.save(report);
    }

    void deleteReport(String reportedDataId) {
        reportRepository.deleteById(reportedDataId);
    }
}
