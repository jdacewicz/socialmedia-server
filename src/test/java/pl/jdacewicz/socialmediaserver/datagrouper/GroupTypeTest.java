package pl.jdacewicz.socialmediaserver.datagrouper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTypeTest {

    @Test
    void should_return_post_when_getting_type_by_lowercase_post_type() {
        //Given
        var type = "post";
        //When
        var result = GroupType.getType(type);
        //Then
        assertEquals(GroupType.POST, result);
    }

    @Test
    void should_return_post_when_getting_type_by_uppercase_post_type() {
        //Given
        var type = "POST";
        //When
        var result = GroupType.getType(type);
        //Then
        assertEquals(GroupType.POST, result);
    }
}