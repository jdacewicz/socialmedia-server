package pl.jdacewicz.socialmediaserver.infrastructure.controller.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserDataReceiverRestController {

    private final ReportDataReceiverFacade reportDataReceiverFacade;

    @PostMapping("/{id}/report")
    public void reportUser(@PathVariable String id,
                           @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, reportRequest, "USER");
    }
}
