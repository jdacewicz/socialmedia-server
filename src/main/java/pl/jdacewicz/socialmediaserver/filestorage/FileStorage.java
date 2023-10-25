package pl.jdacewicz.socialmediaserver.filestorage;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static pl.jdacewicz.socialmediaserver.filestorage.FileStorageActionStatus.*;

class FileStorage {

    static FileStorageActionStatus uploadFile(MultipartFile file, String filePathname){
        try {
            var inputStream = file.getInputStream();
            var directory = new File(filePathname);
            FileUtils.copyInputStreamToFile(inputStream, directory);
        } catch (IOException e) {
            return UPLOAD_FILE_FAILED;
        }
        return UPLOAD_FILE_SUCCESS;
    }

    static FileStorageActionStatus deleteFile(String filePathname) {
        try {
            var directory = new File(filePathname);
            FileUtils.delete(directory);
        } catch (IOException e) {
            return DELETE_FILE_FAILED;
        }
        return DELETE_FILE_SUCCESS;
    }

    static FileStorageActionStatus deleteDirectory(String folderDirectory) {
        try {
            var directory = new File(folderDirectory);
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            return DELETE_DIRECTORY_FAILED;
        }
        return DELETE_DIRECTORY_SUCCESS;
    }
}
