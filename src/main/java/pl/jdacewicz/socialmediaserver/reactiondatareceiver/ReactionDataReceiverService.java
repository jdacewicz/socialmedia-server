package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionUpdateRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
class ReactionDataReceiverService {

    private final ReactionDataReceiverRepository reactionDataReceiverRepository;

    Reaction getReactionById(String reactionId) {
        return reactionDataReceiverRepository.findById(reactionId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    List<Reaction> getAllActiveNotArchivedReactions() {
        var active = true;
        var archived = false;
        return reactionDataReceiverRepository.findAllByActiveAndArchived(active, archived);
    }

    Page<Reaction> getArchivedReactions(Pageable pageable) {
        var archived = true;
        return reactionDataReceiverRepository.findByArchived(archived, pageable);
    }

    Page<Reaction> getReactionsByActive(boolean active, Pageable pageable) {
        var archived = false;
        return reactionDataReceiverRepository.findByActiveAndArchived(active, archived, pageable);
    }

    Reaction createReaction(ReactionRequest reactionRequest, String imageFileName) {
        var reaction = Reaction.builder()
                .name(reactionRequest.name())
                .imageName(imageFileName)
                .active(false)
                .archived(false)
                .build();
        return reactionDataReceiverRepository.save(reaction);
    }

    void updateReactionName(String id, ReactionUpdateRequest reactionUpdateRequest) {
        reactionDataReceiverRepository.updateReactionName(id, reactionUpdateRequest.name());
    }

    void updateReactionActivity(String reactionId, boolean active) {
        reactionDataReceiverRepository.updateReactionActive(reactionId, active);
    }

    void updateReactionArchived(String reactionId, boolean archived) {
        reactionDataReceiverRepository.updateReactionArchived(reactionId, archived);
    }

    void deleteAllReactions() {
        reactionDataReceiverRepository.deleteAll();
    }
}
