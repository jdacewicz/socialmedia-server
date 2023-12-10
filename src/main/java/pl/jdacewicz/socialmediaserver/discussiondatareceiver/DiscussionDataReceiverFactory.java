package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

interface DiscussionDataReceiverFactory {

    DiscussionDataReceiverService<? extends Discussion<?>> getDiscussionDataReceiverService(String type);
}
