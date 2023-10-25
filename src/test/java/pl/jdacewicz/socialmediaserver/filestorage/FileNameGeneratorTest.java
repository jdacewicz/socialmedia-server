package pl.jdacewicz.socialmediaserver.filestorage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileNameGeneratorTest {

    @Test
    void should_return_new_sixteen_characters_name_with_extension_when_generating_file_name_by_name_with_extension() {
        //Given
        var extension = ".jpg";
        var originalFileName = "file" + extension;
        //When
        var result = FileNameGenerator.generateFileName(originalFileName);
        //Then
        assertEquals(20, result.length());
        assertEquals(extension, result.substring(16));
    }

    @Test
    void should_return_new_sixteen_characters_name_without_extension_when_generating_file_name_by_name_without_extension() {
        //Given
        var originalFileName = "file";
        //When
        var result = FileNameGenerator.generateFileName(originalFileName);
        //Then
        assertEquals(16, result.length());
    }
}