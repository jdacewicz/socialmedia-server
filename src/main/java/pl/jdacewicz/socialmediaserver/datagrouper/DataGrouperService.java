package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupParticipator;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.PostGroupRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

@Service
@RequiredArgsConstructor
class DataGrouperService {

    private final UserDataReceiverFacade userDataReceiverFacade;
    private final PostGroupRepository postGroupRepository;

    PostGroup getPostGroupById(String groupId) {
        return postGroupRepository.findById(groupId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    PostGroup createPostGroup(PostGroupRequest postGroupRequest, String imageName) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser()
                .userId();
        var owner = new GroupParticipator(loggedUserId);
        var postGroup = PostGroup.builder()
                .name(postGroupRequest.name())
                .imageName(imageName)
                .owner(owner)
                .build();
        return postGroupRepository.save(postGroup);
    }
}
