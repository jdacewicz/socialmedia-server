package pl.jdacewicz.socialmediaserver.infrastructure.controller.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jdacewicz.socialmediaserver.bangiver.BanGiverFacade;
import pl.jdacewicz.socialmediaserver.bangiver.dto.PermanentBanResponse;
import pl.jdacewicz.socialmediaserver.bangiver.dto.TemporaryBanResponse;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserPermanentBanRequest;
import pl.jdacewicz.socialmediaserver.bangiver.dto.UserTemporaryBanRequest;

@RestController
@RequestMapping(value = "/api/bans")
@RequiredArgsConstructor
public class BanGiverRestController {

    private final BanGiverFacade banGiverFacade;

    @PutMapping("/check")
    public void checkTemporaryBansExpiration() {
        banGiverFacade.checkTemporaryBansExpiration();
    }

    @PutMapping("/user/{userId}/ban/permanent")
    public PermanentBanResponse banUserPermanently(@PathVariable String userId,
                                                   @RequestBody UserPermanentBanRequest userPermanentBanRequest) {
        return banGiverFacade.banUserPermanently(userId, userPermanentBanRequest);
    }

    @PutMapping("/user/{userId}/ban/temporary")
    public TemporaryBanResponse banUserTemporary(@PathVariable String userId,
                                                 @RequestBody UserTemporaryBanRequest userTemporaryBanRequest) {
        return banGiverFacade.banUserTemporary(userId, userTemporaryBanRequest);
    }

    @PutMapping("/user/{userId}/revoke")
    public void revokeUserBans(@PathVariable String userId) {
        banGiverFacade.revokeBansByUserId(userId);
    }
}
