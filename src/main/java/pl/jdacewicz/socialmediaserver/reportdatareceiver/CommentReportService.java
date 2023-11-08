package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.List;

@Service
@RequiredArgsConstructor
class CommentReportService implements ReportService {

    private final CommentReportRepository commentReportRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;


    @Override
    public List<CommentReport> getAllReports() {
        return commentReportRepository.findAll();
    }

    @Override
    public void createReport(String reportedDataId, ReportRequest reportRequest) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
        var reportType = ReportType.getType(reportRequest.reportType());
        var commentReport = CommentReport.builder()
                .commentId(reportedDataId)
                .reportingUserId(loggedUserId)
                .type(reportType)
                .content(reportRequest.content())
                .build();
        commentReportRepository.save(commentReport);
    }

    @Override
    public void deleteReport(String reportedDataId) {
        commentReportRepository.deleteById(reportedDataId);
    }
}
