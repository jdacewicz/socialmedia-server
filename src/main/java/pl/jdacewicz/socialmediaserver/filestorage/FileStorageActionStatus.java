package pl.jdacewicz.socialmediaserver.filestorage;

import lombok.Getter;

@Getter
enum FileStorageActionStatus {
    DELETE_DIRECTORY_SUCCESS("Directory deleted successfully"),
    DELETE_DIRECTORY_FAILED("Failed to delete directory"),
    UPLOAD_FILE_SUCCESS("File uploaded successfully"),
    UPLOAD_FILE_FAILED("Failed to upload file"),
    DELETE_FILE_SUCCESS("File deleted successfully"),
    DELETE_FILE_FAILED("Failed to delete file");

    private final String message;

    FileStorageActionStatus(String message) {
        this.message = message;
    }
}
