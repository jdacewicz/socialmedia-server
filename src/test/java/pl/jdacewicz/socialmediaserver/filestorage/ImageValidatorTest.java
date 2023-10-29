package pl.jdacewicz.socialmediaserver.filestorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class ImageValidatorTest {

    ImageValidator imageValidator;

    @BeforeEach
    void setUp() {
        imageValidator = new ImageValidator();
    }

    @Test
    void should_return_file_validation_result_with_message_success_when_file_content_type_is_image_jpg() {
        //Given
        var image = new MockMultipartFile("name", "file.jpg" , "image/jpg", "content".getBytes());
        //When
        var result = imageValidator.validate(image);
        //Then
        assertEquals(FileValidationResult.validationSuccess()
                .validationMessage(), result.validationMessage());
    }

    @Test
    void should_return_file_validation_result_with_message_success_when_file_content_type_is_image_jpeg() {
        //Given
        var image = new MockMultipartFile("name", "file.jpeg" , "image/jpeg", "content".getBytes());
        //When
        var result = imageValidator.validate(image);
        //Then
        assertEquals(FileValidationResult.validationSuccess()
                .validationMessage(), result.validationMessage());
    }

    @Test
    void should_return_file_validation_result_with_message_success_when_file_content_type_is_image_png() {
        //Given
        var image = new MockMultipartFile("name", "file.png" , "image/png", "content".getBytes());
        //When
        var result = imageValidator.validate(image);
        //Then
        assertEquals(FileValidationResult.validationSuccess()
                .validationMessage(), result.validationMessage());
    }

    @Test
    void should_return_file_validation_result_with_message_validation_error_empty_when_validating_by_empty_file() {
        //Given
        var emptyFile = new MockMultipartFile("file", "file.jpg", "image/jpg", new byte[0]);
        //When
        var result = imageValidator.validate(emptyFile);
        //Then
        assertEquals(ValidationError.EMPTY.getMessage(), result.validationMessage());
    }

    @Test
    void should_return_file_validation_result_with_message_validation_error_missing_extension_when_validating_by_unnamed_file() {
        //Given
        var unnamedImage = new MockMultipartFile("name", "" , "image/png", "content".getBytes());
        //When
        var result = imageValidator.validate(unnamedImage);
        //Then
        assertEquals(ValidationError.MISSING_EXTENSION.getMessage(), result.validationMessage());
    }

    @Test
    void should_return_file_validation_result_with_message_validation_error_missing_extension_when_validating_by_file_without_dot() {
        //Given
        var notProperNamedFile = new MockMultipartFile("name", "file" , "image/png", "content".getBytes());
        //When
        var result = imageValidator.validate(notProperNamedFile);
        //Then
        assertEquals(ValidationError.MISSING_EXTENSION.getMessage(), result.validationMessage());
    }
    @Test
    void should_return_file_validation_result_with_message_validation_error_not_supported_content_type_when_file_content_type_is_text_plain() {
        //Given
        var image = new MockMultipartFile("name", "file.jpeg" , "text/plain", "content".getBytes());
        //When
        var result = imageValidator.validate(image);
        //Then
        assertEquals(ValidationError.NOT_SUPPORTED_CONTENT_TYPE.getMessage(), result.validationMessage());
    }

    @Test
    void should_return_file_validation_result_with_messages_separated_by_colon_when_multiple_validation_errors_occurred() {
        //Given
        var emptyFile = new MockMultipartFile("file", "", "", new byte[0]);
        //When
        var result = imageValidator.validate(emptyFile);
        //Then
        String expected = ValidationError.EMPTY.getMessage() + ", " +
                ValidationError.MISSING_EXTENSION.getMessage() + ", " +
                ValidationError.NOT_SUPPORTED_CONTENT_TYPE.getMessage();
        assertEquals(expected, result.validationMessage());
    }
}