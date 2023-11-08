package pl.jdacewicz.socialmediaserver.reportdatareceiver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTypeTest {

    @Test
    void should_return_user_when_getting_type_by_lowercase_user_type() {
        //Given
        var type = "user";
        //When
        var result = DataType.getType(type);
        //Then
        assertEquals(DataType.USER, result);
    }

    @Test
    void should_return_user_when_getting_type_by_uppercase_user_type() {
        //Given
        var type = "USER";
        //When
        var result = DataType.getType(type);
        //Then
        assertEquals(DataType.USER, result);
    }

    @Test
    void should_return_post_when_getting_type_by_lowercase_post_type() {
        //Given
        var type = "post";
        //When
        var result = DataType.getType(type);
        //Then
        assertEquals(DataType.POST, result);
    }

    @Test
    void should_return_post_when_getting_type_by_uppercase_post_type() {
        //Given
        var type = "POST";
        //When
        var result = DataType.getType(type);
        //Then
        assertEquals(DataType.POST, result);
    }

    @Test
    void should_return_comment_when_getting_type_by_lowercase_comment_type() {
        //Given
        var type = "comment";
        //When
        var result = DataType.getType(type);
        //Then
        assertEquals(DataType.COMMENT, result);
    }

    @Test
    void should_return_comment_when_getting_type_by_uppercase_comment_type() {
        //Given
        var type = "COMMENT";
        //When
        var result = DataType.getType(type);
        //Then
        assertEquals(DataType.COMMENT, result);
    }
}