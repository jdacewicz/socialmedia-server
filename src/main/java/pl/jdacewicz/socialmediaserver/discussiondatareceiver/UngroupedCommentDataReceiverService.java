package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdacewicz.socialmediaserver.bannedwordschecker.BannedWordsCheckerFacade;
import pl.jdacewicz.socialmediaserver.reactionuser.dto.ReactionUser;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Service
@RequiredArgsConstructor
class UngroupedCommentDataReceiverService extends DiscussionDataReceiverService<Comment> implements CommentDataReceiverService<Comment> {

    private final UngroupedPostDataReceiverService ungroupedPostDataReceiverService;
    private final CommentDataReceiverRepository commentDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @Override
    @Transactional
    public Comment createComment(String postId, String content, String imageName, String authenticationHeader) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var foundPost = ungroupedPostDataReceiverService.getDiscussionById(postId);
        var comment = Comment.builder()
                .content(content)
                .creator(loggedUser)
                .imageName(imageName)
                .imageMainDirectory(foundPost.getFolderDirectory())
                .build();
        var createdComment = commentDataReceiverRepository.save(comment);
        ungroupedPostDataReceiverService.updatePost(foundPost.withComment(createdComment));
        return createdComment;
    }

    @Override
    @Transactional
    public Comment reactToDiscussionById(String id, ReactionUser reactionUser) {
        var comment = getDiscussionById(id);
        comment = react(comment, reactionUser);
        return commentDataReceiverRepository.save(comment);
    }

    @Override
    Comment getDiscussionById(String id) {
        return commentDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    Page<Comment> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return commentDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    void deleteDiscussion(Comment discussion) {
        commentDataReceiverRepository.delete(discussion);
    }

    @Override
    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    void deleteAllDiscussions() {
        commentDataReceiverRepository.deleteAll();
    }
}
