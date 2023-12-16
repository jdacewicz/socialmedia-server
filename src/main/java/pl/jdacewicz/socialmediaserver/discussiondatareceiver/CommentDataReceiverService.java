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
class CommentDataReceiverService implements DiscussionDataReceiverService<Comment> {

    private final BasicPostDataReceiverService basicPostDataReceiverService;
    private final CommentDataReceiverRepository commentDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @Override
    public Comment getDiscussionById(String id) {
        return commentDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    public Page<Comment> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return commentDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    @Transactional
    public Comment reactToDiscussionById(String id, ReactionUser reactionUser) {
        var comment = getDiscussionById(id);
        if (comment.isReactionUserStored(reactionUser)) {
            return commentDataReceiverRepository.save(comment.withoutReactionUser(reactionUser));
        }
        return commentDataReceiverRepository.save(comment.withReactionUser(reactionUser));
    }

    @Transactional
    public Comment createBasicComment(String postId, String content, String imageName, String authenticationHeader) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var foundPost = basicPostDataReceiverService.getDiscussionById(postId);
        var comment = Comment.builder()
                .content(content)
                .creator(loggedUser)
                .imageName(imageName)
                .imageMainDirectory(foundPost.getFolderDirectory())
                .build();
        var createdComment = commentDataReceiverRepository.save(comment);
        basicPostDataReceiverService.updatePost(foundPost.withComment(createdComment));
        return createdComment;
    }

    @Override
    public void deleteDiscussionById(String id) {
        commentDataReceiverRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    public void deleteAllDiscussions() {
        commentDataReceiverRepository.deleteAll();
    }
}
