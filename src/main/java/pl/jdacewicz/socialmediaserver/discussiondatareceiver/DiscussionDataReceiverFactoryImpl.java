package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DiscussionDataReceiverFactoryImpl implements DiscussionDataReceiverFactory {

    private final BasicPostDataReceiverService postDataReceiverService;
    private final GroupedPostDataReceiverService groupedPostDataReceiverService;
    private final CommentDataReceiverService commentDataReceiverService;

    @Override
    public DiscussionDataReceiverService<? extends Discussion<?>> getDiscussionDataReceiverService(String discussionType) {
        var type = DiscussionType.valueOf(discussionType);
        switch (type) {
            case BASIC_POST -> {
                return postDataReceiverService;
            }
            case GROUPED_POST -> {
                return groupedPostDataReceiverService;
            }
            case COMMENT -> {
                return commentDataReceiverService;
            }
            default -> throw new IllegalArgumentException("Invalid discussion type: " + type);
        }
    }
}
