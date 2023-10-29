package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.discussiondatareceiver.dto.*;
import pl.jdacewicz.socialmediaserver.filestorage.FileStorageFacade;
import pl.jdacewicz.socialmediaserver.filestorage.dto.DirectoryDeleteRequest;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;
import pl.jdacewicz.socialmediaserver.reactionuser.ReactionUserFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUserRequest;

import java.io.IOException;

@RequiredArgsConstructor
public class DiscussionDataReceiverFacade {

    private final DiscussionDataReceiverService discussionDataReceiverService;
    private final ReactionUserFacade reactionUserFacade;
    private final FileStorageFacade fileStorageFacade;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public PostDto getPostById(String postId) {
        var foundPost = discussionDataReceiverService.getPostById(postId);
        return postMapper.mapToDto(foundPost);
    }

    public CommentDto getCommentById(String commentId) {
        var foundComment = discussionDataReceiverService.getCommentById(commentId);
        return commentMapper.mapToDto(foundComment);
    }

    @Transactional
    public PostDto createPost(MultipartFile postImage, PostRequest postRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(postImage)
                .fileName();
        var createdPost = discussionDataReceiverService.createPost(postRequest.content(), newFileName);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdPost.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(postImage, imageUploadRequest);
        return postMapper.mapToDto(createdPost);
    }

    @Transactional
    public CommentDto createComment(MultipartFile commentImage, CommentRequest commentRequest) throws IOException {
        var newFileName = fileStorageFacade.generateFilename(commentImage)
                .fileName();
        var createdComment = discussionDataReceiverService.createComment(commentRequest.postId(),
                commentRequest.content(), newFileName);
        var imageUploadRequest = FileUploadRequest.builder()
                .fileName(newFileName)
                .fileUploadDirectory(createdComment.getFolderDirectory())
                .build();
        fileStorageFacade.uploadImage(commentImage, imageUploadRequest);
        return commentMapper.mapToDto(createdComment);
    }

    public PostDto reactToPost(PostReactionRequest postReactionRequest) {
        var reactionUserRequest = new ReactionUserRequest(postReactionRequest.reactionId());
        var reactionUser = reactionUserFacade.createReactionUser(reactionUserRequest);
        var reactedPost = discussionDataReceiverService.reactToPostById(postReactionRequest.postId(), reactionUser);
        return postMapper.mapToDto(reactedPost);
    }

    public CommentDto reactToComment(CommentReactionRequest commentReactionRequest) {
        var reactionUserRequest = new ReactionUserRequest(commentReactionRequest.reactionId());
        var reactionUser = reactionUserFacade.createReactionUser(reactionUserRequest);
        var reactedComment = discussionDataReceiverService.reactToCommentById(commentReactionRequest.commentId(), reactionUser);
        return commentMapper.mapToDto(reactedComment);
    }

    @Transactional
    public void deletePost(String postId) throws IOException {
        var foundPost = discussionDataReceiverService.getPostById(postId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundPost.getFolderDirectory());
        discussionDataReceiverService.deletePost(foundPost);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }

    @Transactional
    public void deleteComment(String commentId) throws IOException {
        var foundComment = discussionDataReceiverService.getCommentById(commentId);
        var directoryDeleteRequest = new DirectoryDeleteRequest(foundComment.getFolderDirectory());
        discussionDataReceiverService.deleteComment(foundComment);
        fileStorageFacade.deleteDirectory(directoryDeleteRequest);
    }
}
