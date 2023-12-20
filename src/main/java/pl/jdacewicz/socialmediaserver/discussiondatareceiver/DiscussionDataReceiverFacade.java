package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.DiscussionCreationRequest;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.ReactionUserFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUserRequest;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class DiscussionDataReceiverFacade {

    private final DiscussionDataReceiverFactoryImpl discussionDataReceiverFactoryImpl;
    private final ReactionUserFacade reactionUserFacade;
    private final FileStorageFacade fileStorageFacade;
    private final DiscussionMapper discussionMapper;

    public DiscussionDto getDiscussionById(String discussionId, String discussionType) {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        var foundDiscussion = service.getDiscussionById(discussionId);
        return discussionMapper.mapToDto(foundDiscussion);
    }

    public List<DiscussionDto> getRandomPosts(String discussionType) {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        if (service instanceof PostDataReceiverService<?> postService) {
            var foundPosts = postService.getRandomPosts();
            return discussionMapper.mapToDto(foundPosts);
        }
        throw new UnsupportedOperationException();
    }

    public Page<DiscussionDto> getPostsByUserId(String userId, String discussionType, int pageNumber, int pageSize) {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        if (service instanceof PostDataReceiverService<?> postService) {
            var pageable = PageRequest.of(pageNumber, pageSize);
            var foundPosts = postService.getPostsByCreatorUserId(userId, pageable);
            return foundPosts.map(discussionMapper::mapToDto);
        }
        throw new UnsupportedOperationException();
    }

    public Page<DiscussionDto> getDiscussionsByContentContaining(String phrase, String discussionType, int pageNumber, int pageSize) {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        var pageable = PageRequest.of(pageNumber, pageSize);
        var foundPosts = service.getDiscussionsByContentContaining(phrase, pageable);
        return foundPosts.map(discussionMapper::mapToDto);
    }

    public Set<DiscussionDto> getCommentsByPostId(String postId, String postType, int commentQuantity) {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(postType);
        if (service instanceof PostDataReceiverService<?> postService) {
            var foundComments = postService.getCommentsByPostId(postId, commentQuantity);
            return discussionMapper.mapToDto(foundComments);
        }
        throw new UnsupportedOperationException();
    }

    @Transactional
    public <T extends DiscussionCreationRequest> DiscussionDto createDiscussion(String discussionType, String authenticationHeader,
                                                                                MultipartFile image, T request) throws IOException {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        var newFileName = fileStorageFacade.generateFilename(image)
                .fileName();
        var createdDiscussion = service.createDiscussion(authenticationHeader, newFileName, request);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdDiscussion.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(image, imageUploadRequest);
        return discussionMapper.mapToDto(createdDiscussion);
    }

    @Transactional
    public DiscussionDto reactToDiscussion(String reactionId, String discussionId, String discussionType, String authenticationHeader) {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        var reactionUserRequest = new ReactionUserRequest(reactionId);
        var reactionUser = reactionUserFacade.createReactionUser(authenticationHeader, reactionUserRequest);
        var reactedDiscussion = service.reactToDiscussionById(discussionId, reactionUser);
        return discussionMapper.mapToDto(reactedDiscussion);
    }

    @Transactional
    public void deleteDiscussion(String discussionId, String discussionType) throws IOException {
        var service = discussionDataReceiverFactoryImpl.getDiscussionDataReceiverService(discussionType);
        var foundDiscussion = service.getDiscussionById(discussionId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundDiscussion.getFolderDirectory());
        service.deleteDiscussionById(discussionId);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }
}
