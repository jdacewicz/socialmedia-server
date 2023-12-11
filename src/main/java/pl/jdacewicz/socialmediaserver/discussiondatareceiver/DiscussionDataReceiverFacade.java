package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.CommentRequest;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostDto;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.PostRequest;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.ReactionUserFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUserRequest;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DiscussionDataReceiverFacade {

    private final PostDataReceiverFactory postDataReceiverFactory;
    private final CommentDataReceiverFactory commentDataReceiverFactory;
    private final ReactionUserFacade reactionUserFacade;
    private final FileStorageFacade fileStorageFacade;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public PostDto getPostById(String postId, String postType) {
        var service = postDataReceiverFactory.getDiscussionDataReceiverService(postType);
        var foundPost = service.getDiscussionById(postId);
        return postMapper.mapToDto(foundPost);
    }

    public CommentDto getCommentById(String commentId, String commentType) {
        var service = commentDataReceiverFactory.getDiscussionDataReceiverService(commentType);
        var foundComment = service.getDiscussionById(commentId);
        return commentMapper.mapToDto(foundComment);
    }

    public List<PostDto> getRandomPosts(String postType) {
        var service = postDataReceiverFactory.getDiscussionDataReceiverService(postType);
        var foundPosts = service.getRandomPosts();
        return postMapper.mapToDto(foundPosts);
    }

    public Page<PostDto> getPostsByUserId(String userId, String postType, int pageNumber, int pageSize) {
        var service = postDataReceiverFactory.getDiscussionDataReceiverService(postType);
        var pageable = PageRequest.of(pageNumber, pageSize);
        var foundPosts = service.getPostsByCreatorUserId(userId, pageable);
        return foundPosts.map(postMapper::mapToDto);
    }

    public Page<PostDto> getPostsByContentContaining(String phrase, String postType, int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var service = postDataReceiverFactory.getDiscussionDataReceiverService(postType);
        var foundPosts = service.getDiscussionsByContentContaining(phrase, pageable);
        return foundPosts.map(postMapper::mapToDto);
    }

    public Page<CommentDto> getCommentsByContentContaining(String phrase, String commentType, int pageNumber, int pageSize) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        var service = commentDataReceiverFactory.getDiscussionDataReceiverService(commentType);
        var foundComments = service.getDiscussionsByContentContaining(phrase, pageable);
        return foundComments.map(commentMapper::mapToDto);
    }

    @Transactional
    public PostDto createBasicPost(String authenticationHeader, MultipartFile postImage, PostRequest postRequest) throws IOException {
        var service = (BasicPostDataReceiverService) postDataReceiverFactory.getDiscussionDataReceiverService(PostType.BASIC.name());
        var newFileName = fileStorageFacade.generateFilename(postImage)
                .fileName();
        var createdPost = service.createBasicPost(authenticationHeader, newFileName, postRequest);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdPost.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(postImage, imageUploadRequest);
        return postMapper.mapToDto(createdPost);
    }

    @Transactional
    public PostDto createGroupedPost(String groupId, String authenticationHeader, MultipartFile postImage,
                                     PostRequest postRequest) throws IOException {
        var service = (GroupedPostDataReceiverService) postDataReceiverFactory.getDiscussionDataReceiverService(PostType.GROUPED.name());
        var newFileName = fileStorageFacade.generateFilename(postImage)
                .fileName();
        var createdPost = service.createGroupedPost(groupId, authenticationHeader, newFileName, postRequest);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdPost.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(postImage, imageUploadRequest);
        return postMapper.mapToDto(createdPost);
    }

    @Transactional
    public CommentDto createGroupedComment(String postId, String groupId, String authenticationHeader,
                                           MultipartFile commentImage, CommentRequest commentRequest) throws IOException {
        var service = (GroupedCommentDataReceiverService) commentDataReceiverFactory.getDiscussionDataReceiverService(CommentType.GROUPED.name());
        var newFileName = fileStorageFacade.generateFilename(commentImage)
                .fileName();
        var createdComment = service.createGroupedComment(postId, groupId, commentRequest.content(),
                newFileName, authenticationHeader);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdComment.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(commentImage, imageUploadRequest);
        return commentMapper.mapToDto(createdComment);
    }

    @Transactional
    public CommentDto createBasicComment(String postId, String authenticationHeader, MultipartFile commentImage,
                                         CommentRequest commentRequest) throws IOException {
        var service = (BasicCommentDataReceiverService) commentDataReceiverFactory.getDiscussionDataReceiverService(CommentType.BASIC.name());
        var newFileName = fileStorageFacade.generateFilename(commentImage)
                .fileName();
        var createdComment = service.createBasicComment(postId, commentRequest.content(),
                newFileName, authenticationHeader);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdComment.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(commentImage, imageUploadRequest);
        return commentMapper.mapToDto(createdComment);
    }

    @Transactional
    public PostDto reactToPost(String reactionId, String postId, String postType, String authenticationHeader) {
        var service = postDataReceiverFactory.getDiscussionDataReceiverService(postType);
        var reactionUserRequest = new ReactionUserRequest(reactionId);
        var reactionUser = reactionUserFacade.createReactionUser(authenticationHeader, reactionUserRequest);
        var reactedPost = service.reactToDiscussionById(postId, reactionUser);
        return postMapper.mapToDto(reactedPost);
    }

    @Transactional
    public CommentDto reactToComment(String reactionId, String commentId, String commentType, String authenticationHeader) {
        var service = commentDataReceiverFactory.getDiscussionDataReceiverService(commentType);
        var reactionUserRequest = new ReactionUserRequest(reactionId);
        var reactionUser = reactionUserFacade.createReactionUser(authenticationHeader, reactionUserRequest);
        var reactedComment = service.reactToDiscussionById(commentId, reactionUser);
        return commentMapper.mapToDto(reactedComment);
    }

    @Transactional
    public void deletePost(String postId, String postType) throws IOException {
        var service = postDataReceiverFactory.getDiscussionDataReceiverService(postType);
        var foundPost = service.getDiscussionById(postId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundPost.getFolderDirectory());
        service.deleteDiscussionById(postId);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }

    @Transactional
    public void deleteComment(String commentId, String commentType) throws IOException {
        var service = commentDataReceiverFactory.getDiscussionDataReceiverService(commentType);
        var foundComment = service.getDiscussionById(commentId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundComment.getFolderDirectory());
        service.deleteDiscussionById(commentId);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }
}
