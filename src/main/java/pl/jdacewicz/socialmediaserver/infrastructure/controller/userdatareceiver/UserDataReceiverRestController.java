package pl.jdacewicz.socialmediaserver.infrastructure.controller.userdatareceiver;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    public void reportUser(@PathVariable @NotBlank String id,
                           @RequestBody @Valid ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, reportRequest, "USER");
    }


    @PutMapping("/{id}/ban/permanent")
    public PermanentBanResponse banUserPermanently(@PathVariable @NotBlank String id,
                                                   @RequestBody @Valid UserPermanentBanRequest userPermanentBanRequest) {
        return banGiverFacade.banUserPermanently(id, userPermanentBanRequest);
    }

    @PutMapping("/{id}/ban/temporary")
    public TemporaryBanResponse banUserTemporary(@PathVariable @NotBlank String id,
                                                 @RequestBody @Valid UserTemporaryBanRequest userTemporaryBanRequest) {
        return banGiverFacade.banUserTemporary(id, userTemporaryBanRequest);
    }

    @PutMapping("/{id}/ban/revoke")
    public void revokeUserBans(@PathVariable @NotBlank String id) {
        banGiverFacade.revokeBansByUserId(id);
    }
}
