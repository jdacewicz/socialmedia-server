package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
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
                .getUserId();
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

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllReports() {
        reportRepository.deleteAll();
    }
}
