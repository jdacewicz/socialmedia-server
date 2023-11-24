package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
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
import java.util.Set;

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

    public List<PostDto> getRandomPosts() {
        var randomPosts = discussionDataReceiverService.getRandomPosts();
        return postMapper.mapToDto(randomPosts);
    }

    public List<PostDto> getPostsByUserId(String userId) {
        var foundPosts = discussionDataReceiverService.getPostsByUserId(userId);
        return postMapper.mapToDto(foundPosts);
    }

    public Set<PostDto> getPostsByContentContaining(String phrase) {
        var foundPosts = discussionDataReceiverService.getPostsByContentContaining(phrase);
        return postMapper.mapToDto(foundPosts);
    }

    public Set<CommentDto> getCommentsByContentContaining(String phrase) {
        var foundComments = discussionDataReceiverService.getCommentsByContentContaining(phrase);
        return commentMapper.mapToDto(foundComments);
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

    public PostDto reactToPost(String reactionId, String postId) {
        var reactionUserRequest = new ReactionUserRequest(reactionId);
        var reactionUser = reactionUserFacade.createReactionUser(reactionUserRequest);
        var reactedPost = discussionDataReceiverService.reactToPostById(postId, reactionUser);
        return postMapper.mapToDto(reactedPost);
    }

    public CommentDto reactToComment(String reactionId, String commentId) {
        var reactionUserRequest = new ReactionUserRequest(reactionId);
        var reactionUser = reactionUserFacade.createReactionUser(reactionUserRequest);
        var reactedComment = discussionDataReceiverService.reactToCommentById(commentId, reactionUser);
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
