package pl.jdacewicz.socialmediaserver.filestorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import pl.jdacewicz.socialmediaserver.filestorage.dto.ImageUploadRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FileStorageFacadeTest {

    FileStorageFacade fileStorageFacade;

    FileStorageService fileStorageService;
    ImageValidator imageValidator;

    @BeforeEach
    void setUp() {
        fileStorageService = Mockito.mock(FileStorageService.class);
        imageValidator = Mockito.mock(ImageValidator.class);
        fileStorageFacade = new FileStorageFacade(fileStorageService, imageValidator);
    }

    @Test
    void should_return_file_upload_result_with_upload_result_message_and_new_file_name_when_uploading_image_validation_succeed() {
        //Given
        var image = new MockMultipartFile("name", "image.png", "image/png", "content".getBytes());
        var imageUploadRequest = new ImageUploadRequest(image, "fileUploadDirectory");
        var fileValidationResult = FileValidationResult.validationSuccess();
        var newFileName = "newName";
        try (MockedStatic<FileNameGenerator> fileNameGenerator = Mockito.mockStatic(FileNameGenerator.class)) {
            when(FileNameGenerator.generateFileName(imageUploadRequest.file().getOriginalFilename()))
                    .thenReturn(newFileName);
            when(imageValidator.validate(imageUploadRequest.file())).thenReturn(fileValidationResult);
            when(fileStorageService.uploadFile(imageUploadRequest.file(), newFileName, imageUploadRequest.fileUploadDirectory()))
                    .thenReturn(FileStorageActionStatus.UPLOAD_FILE_SUCCESS.getMessage());
            //When
            var result = fileStorageFacade.uploadImage(imageUploadRequest);
            //Then
            assertEquals(FileStorageActionStatus.UPLOAD_FILE_SUCCESS.getMessage(), result.message());
            assertEquals(newFileName, result.fileName());
            assertEquals(imageUploadRequest.fileUploadDirectory(), result.folderDirectory());
        }
    }

    @Test
    void should_return_file_upload_result_with_validation_message_when_uploading_image_validation_failed() {
        //Given
        var image = new MockMultipartFile("name", "content".getBytes());
        var imageUploadRequest = new ImageUploadRequest(image, "fileUploadDirectory");
        var fileValidationResult = FileValidationResult.validationFailed("failed");
        when(imageValidator.validate(imageUploadRequest.file())).thenReturn(fileValidationResult);
        //When
        var result = fileStorageFacade.uploadImage(imageUploadRequest);
        //Then
        assertEquals(fileValidationResult.validationMessage(), result.message());
    }
}