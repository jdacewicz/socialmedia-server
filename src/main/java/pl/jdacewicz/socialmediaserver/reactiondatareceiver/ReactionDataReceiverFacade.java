package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionDto;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionRequest;
import pl.jdacewicz.socialmediaserver.reactiondatareceiver.dto.ReactionUpdateRequest;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ReactionDataReceiverFacade {

    private final ReactionDataReceiverService reactionDataReceiverService;
    private final FileStorageFacade fileStorageFacade;
    private final ReactionMapper reactionMapper;

    public ReactionDto getReactionById(String reactionId) {
        var foundReaction = reactionDataReceiverService.getReactionById(reactionId);
        return reactionMapper.mapToDto(foundReaction);
    }

    public List<ReactionDto> getAllActiveReactions() {
        var foundActiveReaction = reactionDataReceiverService.getAllActiveNotArchivedReactions();
        return reactionMapper.mapToDto(foundActiveReaction);
    }

    public Page<ReactionDto> getArchivedReactions(int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var foundReactions = reactionDataReceiverService.getArchivedReactions(pageable);
        return foundReactions.map(reactionMapper::mapToDto);
    }

    public Page<ReactionDto> getReactionsByActive(boolean active, int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var foundReactions = reactionDataReceiverService.getReactionsByActive(active, pageable);
        return foundReactions.map(reactionMapper::mapToDto);
    }

    public Page<ReactionDto> getReactionsByNameContaining(String name, int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var foundReactions = reactionDataReceiverService.getReactionsByNameContaining(name, pageable);
        return foundReactions.map(reactionMapper::mapToDto);
    }

    @Transactional
    public ReactionDto createReaction(MultipartFile reactionImage, ReactionRequest reactionRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(reactionImage)
                .fileName();
        var createdReaction = reactionDataReceiverService.createReaction(reactionRequest, newFileName);
        var fileUploadRequest = new FileUploadRequest(newFileName, createdReaction.getReactionFolderDirectory());
        fileStorageFacade.uploadImage(reactionImage, fileUploadRequest);
        return reactionMapper.mapToDto(createdReaction);
    }

    @Transactional
    public void updateReaction(String reactionId, MultipartFile reactionImage, ReactionUpdateRequest reactionUpdateRequest)
            throws IOException {
        reactionDataReceiverService.updateReactionName(reactionId, reactionUpdateRequest);
        if (reactionImage != null) {
            var reaction = reactionDataReceiverService.getReactionById(reactionId);
            var fileUploadRequest = new FileUploadRequest(reaction.imageName(), reaction.getReactionFolderDirectory());
            fileStorageFacade.uploadImage(reactionImage, fileUploadRequest);
        }
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

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllReactionsData() throws IOException {
        var deleteDirectoryRequest = new DirectoryDeleteRequest(Reaction.MAIN_DIRECTORY);
        reactionDataReceiverService.deleteAllReactions();
        fileStorageFacade.deleteDirectory(deleteDirectoryRequest);
    }
}
