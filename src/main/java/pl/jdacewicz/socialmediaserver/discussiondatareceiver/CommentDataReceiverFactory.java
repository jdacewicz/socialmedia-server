package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CommentDataReceiverFactory implements DiscussionDataReceiverFactory {

    private final BasicCommentDataReceiverService commentDataReceiverService;
    private final GroupedCommentDataReceiverService groupedCommentDataReceiverService;

    @Override
    public CommentDataReceiverService<? extends Comment> getDiscussionDataReceiverService(String type) {
        var commentType = CommentType.valueOf(type);
        switch (commentType) {
            case BASIC -> {
                return commentDataReceiverService;
            }
            case GROUPED -> {
                return groupedCommentDataReceiverService;
            }
            default -> throw new IllegalArgumentException("Invalid comment type: " + commentType);
        }
    }
}
