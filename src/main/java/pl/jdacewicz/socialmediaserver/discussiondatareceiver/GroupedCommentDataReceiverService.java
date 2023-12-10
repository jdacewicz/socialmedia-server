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
class GroupedCommentDataReceiverService implements CommentDataReceiverService<GroupedComment> {

    private final GroupedCommentDataReceiverRepository groupedCommentDataReceiverRepository;
    private final GroupedPostDataReceiverService groupedPostDataReceiverService;
    private final UserDataReceiverFacade userDataReceiverFacade;
    private final BannedWordsCheckerFacade bannedWordsCheckerFacade;

    @Override
    public GroupedComment getDiscussionById(String id) {
        return groupedCommentDataReceiverRepository.findById(id)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    public Page<GroupedComment> getDiscussionsByContentContaining(String phrase, Pageable pageable) {
        return groupedCommentDataReceiverRepository.findByContentContaining(phrase, pageable);
    }

    @Transactional
    public GroupedComment createGroupedComment(String postId, String groupId, String content, String imageName, String authenticationHeader) {
        bannedWordsCheckerFacade.checkForBannedWords(content);
        var loggedUser = userDataReceiverFacade.getLoggedInUser(authenticationHeader);
        var foundPost = groupedPostDataReceiverService.getDiscussionById(postId);
        var comment = GroupedComment.builder()
                .groupId(groupId)
                .content(content)
                .creator(loggedUser)
                .imageName(imageName)
                .imageMainDirectory(foundPost.getFolderDirectory())
                .build();
        var createdComment = groupedCommentDataReceiverRepository.save(comment);
        groupedPostDataReceiverService.updatePost(foundPost.withComment(createdComment));
        return createdComment;
    }

    @Override
    public GroupedComment reactToDiscussionById(String id, ReactionUser reactionUser) {
        var comment = getDiscussionById(id);
        if (comment.isReactionUserStored(reactionUser)) {
            return groupedCommentDataReceiverRepository.save(comment.withoutReactionUser(reactionUser));
        }
        return groupedCommentDataReceiverRepository.save(comment.withReactionUser(reactionUser));
    }

    @Override
    public void deleteDiscussionById(String id) {
        groupedCommentDataReceiverRepository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "${application.scheduled-tasks.delete-all-data.cron}")
    @Profile("demo")
    public void deleteAllDiscussions() {
        groupedCommentDataReceiverRepository.deleteAll();
    }
}
