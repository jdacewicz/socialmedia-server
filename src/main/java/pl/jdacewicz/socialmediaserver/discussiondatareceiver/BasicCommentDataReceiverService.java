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
class BasicCommentDataReceiverService implements CommentDataReceiverService<BasicComment> {

    private final BasicPostDataReceiverService basicPostDataReceiverService;
    private final BasicCommentDataReceiverRepository basicCommentDataReceiverRepository;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @Override
    public BasicComment getDiscussionById(String id) {
        return basicCommentDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    public Page<BasicComment> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return basicCommentDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Override
    @Transactional
    public BasicComment reactToDiscussionById(String id, ReactionUser reactionUser) {
        var comment = getDiscussionById(id);
        if (comment.isReactionUserStored(reactionUser)) {
            return basicCommentDataReceiverRepository.save(comment.withoutReactionUser(reactionUser));
        }
        return basicCommentDataReceiverRepository.save(comment.withReactionUser(reactionUser));
    }

    @Transactional
    public BasicComment createBasicComment(String postId, String content, String imageName, String authenticationHeader) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var foundPost = basicPostDataReceiverService.getDiscussionById(postId);
        var comment = BasicComment.builder()
                .content(content)
                .creator(loggedUser)
                .imageName(imageName)
                .imageMainDirectory(foundPost.getFolderDirectory())
                .build();
        var createdComment = basicCommentDataReceiverRepository.save(comment);
        basicPostDataReceiverService.updatePost(foundPost.withComment(createdComment));
        return createdComment;
    }

    @Override
    public void deleteDiscussionById(String id) {
        basicCommentDataReceiverRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    public void deleteAllDiscussions() {
        basicCommentDataReceiverRepository.deleteAll();
    }
}
