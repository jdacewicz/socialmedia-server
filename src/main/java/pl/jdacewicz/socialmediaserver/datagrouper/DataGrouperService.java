package pl.jdacewicz.socialmediaserver.datagrouper;

import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupRequest;

interface DataGrouperService<T extends Group> {

    T getGroupById(String groupId);

    T createGroup(String authenticationHeader, GroupRequest groupRequest, String groupImageName);

    void deleteGroupById(String groupId);

    void deleteAllGroups();
}
