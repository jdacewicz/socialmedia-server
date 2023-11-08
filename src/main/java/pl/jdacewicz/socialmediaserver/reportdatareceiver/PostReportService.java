package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class PostReportService implements ReportService {

    private final PostReportRepository postReportRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;

    @Override
    public List<PostReport> getAllReports() {
        return postReportRepository.findAll();
    }

    @Override
    public void createReport(String reportedDataId, ReportRequest reportRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
        var reportType = ReportType.getType(reportRequest.reportType());
        var postReport = PostReport.builder()
                .postId(reportedDataId)
                .reportingUserId(loggedUserId)
                .type(reportType)
                .content(reportRequest.content())
                .build();
        postReportRepository.save(postReport);
    }

    @Override
    public void deleteReport(String reportedDataId) {
        postReportRepository.deleteById(reportedDataId);
    }
}
