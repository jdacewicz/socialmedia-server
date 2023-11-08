package pl.jdacewicz.socialmediaserver.infrastructure.controller.reportdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportDto;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/reports")
@RequiredArgsConstructor
public class ReportDataReceiverRestController {

    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @GetMapping
    public Set<ReportDto> getReports(@RequestParam String dataType) {
        return reportDataReceiverFacade.getReports(dataType);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable String id) {
        reportDataReceiverFacade.deleteReport(id);
    }
}
