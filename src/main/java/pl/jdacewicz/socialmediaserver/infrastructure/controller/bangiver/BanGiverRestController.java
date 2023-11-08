package pl.jdacewicz.socialmediaserver.infrastructure.controller.bangiver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdacewicz.socialmediaserver.bangiver.BanGiverFacade;

@RestController
@RequestMapping(value = "/api/bans")
@RequiredArgsConstructor
public class BanGiverRestController {

    private final BanGiverFacade banGiverFacade;

    @PutMapping("/check")
    public void checkTemporaryBansExpiration() {
        banGiverFacade.checkTemporaryBansExpiration();
    }
}
