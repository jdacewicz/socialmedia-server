package pl.jdacewicz.socialmediaserver.filestorage;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

class FileStorage {

    static FileUploadStatus uploadFile(MultipartFile file, String filePathname){
        try {
            var inputStream = file.getInputStream();
            var directory = new File(filePathname);
            FileUtils.copyInputStreamToFile(inputStream, directory);
        } catch (IOException e) {
            return FileUploadStatus.FAILED;
        }
        return FileUploadStatus.SUCCESS;
    }

    static FileDeleteStatus deleteFile(String filePathname) {
        try {
            var directory = new File(filePathname);
            FileUtils.delete(directory);
        } catch (IOException e) {
            return FileDeleteStatus.FAILED;
        }
        return FileDeleteStatus.SUCCESS;
    }

    static FileStorageActionStatus deleteDirectory(String folderDirectory) {
        try {
            var directory = new File(folderDirectory);
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            return FileStorageActionStatus.FAILED;
        }
        return FileStorageActionStatus.SUCCESS;
    }
}
