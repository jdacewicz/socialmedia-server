package pl.jdacewicz.socialmediaserver.discussiondatareceiver;

interface CommentDataReceiverService<T extends Comment> {

    T createComment(String postId, String content, String imageName, String authenticationHeader);
}
