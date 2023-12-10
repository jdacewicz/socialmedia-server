package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PostDataReceiverFactory implements DiscussionDataReceiverFactory {

    private final BasicPostDataReceiverService postDataReceiverService;
    private final GroupedPostDataReceiverService groupedPostDataReceiverService;

    @Override
    public PostDataReceiverService<? extends Post> getDiscussionDataReceiverService(String type) {
        var postType = PostType.valueOf(type);
        switch (postType) {
            case BASIC -> {
                return postDataReceiverService;
            }
            case GROUPED -> {
                return groupedPostDataReceiverService;
            }
            default -> throw new IllegalArgumentException("Invalid post type: " + postType);
        }
    }
}
