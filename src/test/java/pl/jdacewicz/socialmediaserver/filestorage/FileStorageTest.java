package pl.jdacewicz.socialmediaserver.filestorage;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class FileStorageTest {

    @Test
    void should_return_upload_file_success_when_no_error_occurred_while_uploading_file() {
        //Given
        var file = new MockMultipartFile("name", "content".getBytes());
        var filePathname = "path";
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
            //When
            var result = FileStorage.uploadFile(file, filePathname);
            //Then
            assertEquals(FileStorageActionStatus.UPLOAD_FILE_SUCCESS, result);
        }
    }

    @Test
    void should_return_upload_file_failed_when_an_error_occurred_while_uploading_file() {
        //Given
        var file = new MockMultipartFile("name", "content".getBytes());
        var filePathname = "path";
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
            fileUtils.when(() -> FileUtils.copyInputStreamToFile(any(), any())).thenThrow(IOException.class);
            //When
            var result = FileStorage.uploadFile(file, filePathname);
            //Then
            assertEquals(FileStorageActionStatus.UPLOAD_FILE_FAILED, result);
        }
    }

    @Test
    void should_return_delete_file_success_when_no_error_occurred_while_deleting_file() {
        //Given
        var filePathname = "path";
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
            //When
            var result = FileStorage.deleteFile(filePathname);
            //Then
            assertEquals(FileStorageActionStatus.DELETE_FILE_SUCCESS, result);
        }
    }

    @Test
    void should_return_delete_file_failed_when_an_error_occurred_while_deleting_file() {
        //Given
        var filePathname = "path";
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
            fileUtils.when(() -> FileUtils.delete(any())).thenThrow(IOException.class);
            //When
            var result = FileStorage.deleteFile(filePathname);
            //Then
            assertEquals(FileStorageActionStatus.DELETE_FILE_FAILED, result);
        }
    }

    @Test
    void should_return_delete_directory_success_when_no_error_occurred_while_deleting_directory() {
        //Given
        var folderDirectory = "directory";
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
            //When
            var result = FileStorage.deleteDirectory(folderDirectory);
            //Then
            assertEquals(FileStorageActionStatus.DELETE_DIRECTORY_SUCCESS, result);
        }
    }

    @Test
    void should_return_delete_directory_failed_when_an_error_occurred_while_deleting_directory() {
        //Given
        var folderDirectory = "directory";
        try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
            fileUtils.when(() -> FileUtils.deleteDirectory(any())).thenThrow(IOException.class);
            //When
            var result = FileStorage.deleteDirectory(folderDirectory);
            //Then
            assertEquals(FileStorageActionStatus.DELETE_DIRECTORY_FAILED, result);
        }
    }
}