package pl.jdacewicz.socialmediaserver.reactionuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUserRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Service
@RequiredArgsConstructor
class ReactionUserService {

    private final ReactionDataReceiverFacade reactionDataReceiverFacade;
    private final UserDataReceiverFacade userDataReceiverFacade;

    ReactionUser createReactionUser(ReactionUserRequest reactionUserRequest) {
        var reaction = reactionDataReceiverFacade.getReactionById(reactionUserRequest.reactionId());
        var loggedUser = userDataReceiverFacade.getLoggedInUser();
        return ReactionUser.builder()
                .reactionId(reaction.reactionId())
                .userId(loggedUser.userId())
                .build();
    }
}
