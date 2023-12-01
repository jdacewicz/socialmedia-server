package pl.jdacewicz.socialmediaserver.datagrouper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupRequest;

interface DataGrouperService<T extends Group> {

    T getGroupById(String groupId);

    Page<T> getGroupsByParticipantId(String participantId, Pageable pageable);

    T createGroup(String authenticationHeader, GroupRequest groupRequest, String groupImageName);

    void deleteGroupById(String groupId);

    void deleteAllGroups();
}
