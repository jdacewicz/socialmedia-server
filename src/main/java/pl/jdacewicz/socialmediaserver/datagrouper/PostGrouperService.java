package pl.jdacewicz.socialmediaserver.datagrouper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupParticipator;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;

import java.util.Set;

@Service
@RequiredArgsConstructor
class PostGrouperService implements DataGrouperService<PostGroup> {

    private final UserDataReceiverFacade userDataReceiverFacade;
    private final PostGroupRepository postGroupRepository;

    @Override
    public PostGroup getGroupById(String groupId) {
        return postGroupRepository.findById(groupId)
                .orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    public Page<PostGroup> getGroupsByParticipantId(String participantId, Pageable pageable) {
        return postGroupRepository.findByParticipants(participantId, pageable);
    }

    @Override
    public PostGroup createGroup(String authenticationHeader, GroupRequest groupRequest, String imageName) {
        var loggedUserId = userDataReceiverFacade.getLoggedInUser(authenticationHeader)
                .getUserId();
        var owner = new GroupParticipator(loggedUserId);
        var postGroup = PostGroup.builder()
                .name(groupRequest.name())
                .imageName(imageName)
                .owner(owner)
                .participants(Set.of(owner))
                .admins(Set.of(owner))
                .build();
        return postGroupRepository.save(postGroup);
    }

    @Override
    public void deleteGroupById(String groupId) {
        postGroupRepository.deleteById(groupId);
    }

    @Override
    public void deleteAllGroups() {
        postGroupRepository.deleteAll();
    }
}
