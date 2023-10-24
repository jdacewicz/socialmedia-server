package pl.jdacewicz.socialmediaserver.filestorage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
class FileStorageService {

    String uploadFile(MultipartFile file, String fileName, String fileDirectory) {
        var filePathname = getPathname(fileName, fileDirectory);
        return FileStorage.uploadFile(file, filePathname)
                .getMessage();
    }

    String deleteFile(String fileName, String fileDirectory) {
        var filePathname = getPathname(fileName, fileDirectory);
        return FileStorage.deleteFile(filePathname)
                .getMessage();
    }

    String deleteDirectory(String directoryPathname) {
        return FileStorage.deleteDirectory(directoryPathname)
                .getMessage();
    }

    private String getPathname(String fileName, String fileDirectory) {
        return fileDirectory + "/" + fileName;
    }
}
