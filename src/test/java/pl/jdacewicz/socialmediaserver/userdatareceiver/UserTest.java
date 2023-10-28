package pl.jdacewicz.socialmediaserver.userdatareceiver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void should_return_main_directory_when_getting_folder_directory_of_user_with_default_profile_picture() {
        //Given
        var user = User.builder()
                .userId("id")
                .profilePictureName("picture.jpg")
                .build();
        //When
        var result = user.getFolderDirectory();
        //Then
        assertEquals(User.MAIN_DIRECTORY + "/" + user.userId(), result);
    }

    @Test
    void should_return_proper_directory_when_getting_folder_directory_of_user_with_not_default_profile_picture() {
        //Given
        var user = User.builder()
                .userId("id")
                .build();
        //When
        var result = user.getFolderDirectory();
        //Then
        assertEquals(User.MAIN_DIRECTORY, result);
    }
}