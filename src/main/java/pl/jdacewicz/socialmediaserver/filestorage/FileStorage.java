package pl.jdacewicz.socialmediaserver.filestorage;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

class FileStorage {

    static void uploadFile(MultipartFile file, String fileName, String fileDirectory) throws IOException {
        var inputStream = file.getInputStream();
        var pathname = getPathname(fileName, fileDirectory);
        var directory = new File(pathname);
        FileUtils.copyInputStreamToFile(inputStream, directory);
    }

    static void deleteFile(String fileName, String fileDirectory) throws IOException {
        var pathname = getPathname(fileName, fileDirectory);
        var directory = new File(pathname);
        FileUtils.delete(directory);
    }

    static void deleteDirectory(String folderDirectory) throws IOException {
        var directory = new File(folderDirectory);
        FileUtils.deleteDirectory(directory);
    }

    private static String getPathname(String fileName, String fileDirectory) {
        return String.format("%s/%s", fileDirectory, fileName);
    }
}
