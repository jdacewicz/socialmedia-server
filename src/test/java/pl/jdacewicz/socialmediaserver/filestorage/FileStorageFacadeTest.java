package pl.jdacewicz.socialmediaserver.filestorage;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import pl.jdacewicz.socialmediaserver.filestorage.dto.FileUploadRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class FileStorageFacadeTest {

    FileStorageFacade fileStorageFacade;

    ImageValidator imageValidator;

    @BeforeEach
    void setUp() {
        imageValidator = Mockito.mock(ImageValidator.class);
        fileStorageFacade = new FileStorageFacade(imageValidator);
    }

    @Test
    void should_throw_validation_exception_when_uploading_invalid_image() {
        //Given
        var image = new MockMultipartFile("name", "content".getBytes());
        FileUploadRequest fileUploadRequest = FileUploadRequest.builder()
                .build();
        var fileValidationResult = FileValidationResult.validationFailed("failed");
        when(imageValidator.validate(image)).thenReturn(fileValidationResult);
        //When
        //Then
        assertThrows(ValidationException.class,
                () -> fileStorageFacade.uploadImage(image, fileUploadRequest));
    }
}