package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

@Service
@RequiredArgsConstructor
class ReactionDataReceiverService {

    private final ReactionDataReceiverRepository reactionDataReceiverRepository;

    Reaction getReactionById(String reactionId) {
        return reactionDataReceiverRepository.findById(reactionId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    Reaction createReaction(ReactionRequest reactionRequest) {
        var reaction = Reaction.builder()
                .name(reactionRequest.name())
                .build();
        return reactionDataReceiverRepository.save(reaction);
    }
}
