package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ReactionDataReceiverFacade {

    private final ReactionDataReceiverService reactionDataReceiverService;
    private final FileStorageFacade fileStorageFacade;

    public ReactionDto getReactionById(String reactionId) {
        var foundReaction = reactionDataReceiverService.getReactionById(reactionId);
        return mapToDto(foundReaction);
    }

    public List<ReactionDto> getAllReactions() {
        return reactionDataReceiverService.getAllReactions()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public ReactionDto createReaction(MultipartFile reactionImage, ReactionRequest reactionRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(reactionImage)
                .fileName();
        var createdReaction = reactionDataReceiverService.createReaction(reactionRequest);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdReaction.getReactionFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(reactionImage, imageUploadRequest);
        return mapToDto(createdReaction);
    }

    public void activateReaction(String reactionId) {
        reactionDataReceiverService.updateReactionActivity(reactionId, true);
    }

    public void deactivateReaction(String reactionId) {
        reactionDataReceiverService.updateReactionActivity(reactionId, false);
    }

    public void archiveReaction(String reactionId) {
        reactionDataReceiverService.updateReactionArchived(reactionId, true);
    }

    public void unarchiveReaction(String reactionId) {
        reactionDataReceiverService.updateReactionArchived(reactionId, false);
    }

    private ReactionDto mapToDto(Reaction reaction) {
        return new ReactionDto(reaction.reactionId(), reaction.getReactionImageDirectory());
    }
}
