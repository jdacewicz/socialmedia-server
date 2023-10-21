package pl.jdacewicz.socialmediaserver.reactionuserpreparer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.ReactionDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.reactionuserpreparer.dto.ReactionUserRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Service
@RequiredArgsConstructor
class ReactionUserPreparerService {

    private final ReactionDataReceiverFacade reactionDataReceiverFacade;
    private final UserDataReceiverFacade userDataReceiverFacade;

    public ReactionUser prepareReactionUser(ReactionUserRequest reactionUserRequest) {
        var reactionDto = reactionDataReceiverFacade.getReactionById(reactionUserRequest.reactionId());
        var userDto = userDataReceiverFacade.getLoggedInUser();
        return new ReactionUser(reactionDto, userDto);
    }
}
