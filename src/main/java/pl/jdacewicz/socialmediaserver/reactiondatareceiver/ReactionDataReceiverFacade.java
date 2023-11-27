package pl.jdacewicz.socialmediaserver.reactiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.filemapper.FileMapperFacade;
import pl.jdacewicz.socialmediaserver.filemapper.dto.MapRequest;
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
    private final FileMapperFacade fileMapperFacade;

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
        var createdReaction = reactionDataReceiverService.createReaction(reactionRequest);
        var newFileName = fileStorageFacade.generateFilename(reactionImage)
                .fileName();
        var fileUploadRequest = mapToFileUploadRequest(newFileName, createdReaction.getReactionFolderDirectory());
        fileStorageFacade.uploadImage(reactionImage, fileUploadRequest);
        return mapToDto(createdReaction);
    }

    @Transactional
    public void updateReaction(String reactionId, MultipartFile reactionImage, ReactionUpdateRequest reactionUpdateRequest)
            throws IOException {
        reactionDataReceiverService.updateReactionName(reactionId, reactionUpdateRequest);
        if (reactionImage != null) {
            var reaction = reactionDataReceiverService.getReactionById(reactionId);
            var fileUploadRequest = mapToFileUploadRequest(reaction.imageName(), reaction.getReactionFolderDirectory());
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

    private ReactionDto mapToDto(Reaction reaction) {
        var image = fileMapperFacade.mapToFile(new MapRequest(reaction.imageName(), reaction.getReactionFolderDirectory()));
        return ReactionDto.builder()
                .reactionId(reaction.reactionId())
                .image(image)
                .build();
    }

    private FileUploadRequest mapToFileUploadRequest(String imageName, String folderDirectory) {
        return FileUploadRequest.builder()
                .fileName(imageName)
                .fileUploadDirectory(folderDirectory)
                .build();

    }

    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllReactionsData() throws IOException {
        var deleteDirectoryRequest = new DirectoryDeleteRequest(Reaction.MAIN_DIRECTORY);
        reactionDataReceiverService.deleteAllReactions();
        fileStorageFacade.deleteDirectory(deleteDirectoryRequest);
    }
}
