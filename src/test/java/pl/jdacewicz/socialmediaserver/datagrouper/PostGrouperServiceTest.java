package pl.jdacewicz.socialmediaserver.datagrouper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.jdacewicz.socialmediaserver.datagrouper.dto.GroupRequest;
import pl.jdacewicz.socialmediaserver.userdatareceiver.UserDataReceiverFacade;
import pl.jdacewicz.socialmediaserver.userdatareceiver.dto.LoggedUserDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PostGrouperServiceTest {

    PostGrouperService postGrouperService;

    UserDataReceiverFacade userDataReceiverFacade;
    PostGroupRepositoryTest postGroupRepositoryTest;

    @BeforeEach
    void setUp() {
        userDataReceiverFacade = Mockito.mock(UserDataReceiverFacade.class);
        postGroupRepositoryTest = new PostGroupRepositoryTest();
        postGrouperService = new PostGrouperService(userDataReceiverFacade, postGroupRepositoryTest);
    }

    @Test
    void should_return_post_group_when_getting_group_by_existing_id() {
        //Given
        var groupId = "existing id";
        var group = PostGroup.builder()
                .groupId(groupId)
                .build();
        postGroupRepositoryTest.save(group);
        //When
        var result = postGrouperService.getGroupById(groupId);
        //Then
        assertEquals(group, result);
    }

    @Test
    void should_throw_unsupported_operation_exception_when_getting_post_by_not_existing_id() {
        //Given
        var groupId = "not existing id";
        //When
        //Then
        assertThrows(UnsupportedOperationException.class,
                () -> postGrouperService.getGroupById(groupId));
    }

    @Test
    void should_return_created_group_with_owner_as_admin_and_participant_when_creating_group() {
        //Given
        var groupRequest = new GroupRequest("name");
        var imageName = "imageName";
        var loggedUser = LoggedUserDto.builder()
                .userId("id")
                .build();
        when(userDataReceiverFacade.getLoggedInUser()).thenReturn(loggedUser);
        //When
        var result = postGrouperService.createGroup(groupRequest, imageName);
        //Then
        assertEquals(loggedUser.getUserId(), result.getOwner()
                .userId());
        assertFalse(result.getAdmins()
                .isEmpty());
        assertFalse(result.getParticipants()
                .isEmpty());
    }
}