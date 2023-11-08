package pl.jdacewicz.socialmediaserver.infrastructure.controller.userdatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.bangiver.BanGiverFacade;
import pl.jdacewicz.socialmediaserver.bangiver.dto.PermanentBanResponse;
import pl.jdacewicz.socialmediaserver.bangiver.dto.TemporaryBanResponse;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.ReportDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reportdatareceiver.dto.ReportRequest;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserDataReceiverRestController {

    private final ReportDataReceiverFacade reportDataReceiverFacade;
    private final BanGiverFacade banGiverFacade;

    @PostMapping("/{id}/report")
    public void reportUser(@PathVariable String id,
                           @RequestBody ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, reportRequest, "USER");
    }


    @PutMapping("/{id}/ban/permanent")
    public PermanentBanResponse banUserPermanently(@PathVariable String id,
                                                   @RequestBody UserPermanentBanRequest userPermanentBanRequest) {
        return banGiverFacade.banUserPermanently(id, userPermanentBanRequest);
    }

    @PutMapping("/{id}/ban/temporary")
    public TemporaryBanResponse banUserTemporary(@PathVariable String id,
                                                 @RequestBody UserTemporaryBanRequest userTemporaryBanRequest) {
        return banGiverFacade.banUserTemporary(id, userTemporaryBanRequest);
    }

    @PutMapping("/{id}/ban/revoke")
    public void revokeUserBans(@PathVariable String id) {
        banGiverFacade.revokeBansByUserId(id);
    }
}
