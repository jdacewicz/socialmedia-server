package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

import java.util.List;

interface ReportService {

    List<? extends Report> getAllReports();

    void createReport(String reportedDataId, ReportRequest reportRequest);

    void deleteReport(String reportedDataId);
}
