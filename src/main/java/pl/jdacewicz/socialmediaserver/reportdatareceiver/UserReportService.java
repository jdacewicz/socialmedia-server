package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserReportService implements ReportService {

    private final UserReportRepository userReportRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Override
    public List<UserReport> getAllReports() {
        return userReportRepository.findAll();
    }

    @Override
    public void createReport(String reportedDataId, ReportRequest reportRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
        var reportType = ReportType.getType(reportRequest.reportType());
        var userReport = UserReport.builder()
                .userId(reportedDataId)
                .reportingUserId(loggedUserId)
                .type(reportType)
                .content(reportRequest.content())
                .build();
        userReportRepository.save(userReport);
    }

    @Override
    public void deleteReport(String reportedDataId) {
        userReportRepository.deleteById(reportedDataId);
    }
}
