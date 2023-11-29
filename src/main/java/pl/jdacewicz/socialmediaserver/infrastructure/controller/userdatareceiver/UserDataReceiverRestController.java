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
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.UserDto;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserDataReceiverRestController {

    private final UserDataReceiverFacade userDataReceiverFacade;
    private final ReportDataReceiverFacade reportDataReceiverFacade;
    private final BanGiverFacade banGiverFacade;

    @GetMapping
    public UserDto getLoggedInUser(@RequestHeader("Authorization") String authorizationHeader) {
        return userDataReceiverFacade.getLoggedInUser(authorizationHeader);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return userDataReceiverFacade.getUserById(id);
    }

    @PostMapping("/{id}/report")
    public void reportUser(@RequestHeader("Authorization") String authorizationHeader,
                           @PathVariable @NotBlank String id,
                           @RequestBody @Valid ReportRequest reportRequest) {
        reportDataReceiverFacade.report(id, authorizationHeader, reportRequest, "USER");
    }


    @PutMapping("/{id}/ban/permanent")
    public PermanentBanResponse banUserPermanently(@RequestHeader("Authorization") String authorizationHeader,
                                                   @PathVariable @NotBlank String id,
                                                   @RequestBody @Valid UserPermanentBanRequest userPermanentBanRequest) {
        return banGiverFacade.banUserPermanently(id, authorizationHeader, userPermanentBanRequest);
    }

    @PutMapping("/{id}/ban/temporary")
    public TemporaryBanResponse banUserTemporary(@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable @NotBlank String id,
                                                 @RequestBody @Valid UserTemporaryBanRequest userTemporaryBanRequest) {
        return banGiverFacade.banUserTemporary(id, authorizationHeader, userTemporaryBanRequest);
    }

    @PutMapping("/{id}/ban/revoke")
    public void revokeUserBans(@PathVariable @NotBlank String id) {
        banGiverFacade.revokeBansByUserId(id);
    }
}
