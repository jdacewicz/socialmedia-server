package pl.jdacewicz.socialmediaserver.filestorage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pl.jdacewicz.socialmediaserver.filestorage.dto.*;

@RequiredArgsConstructor
public class FileStorageFacade {

    private final FileStorageService fileStorageService;
    private final ImageValidator imageValidator;

    public FileUploadResult uploadImage(ImageUploadRequest imageUploadRequest) {
        var image = imageUploadRequest.file();
        var fileValidationResult = imageValidator.validate(image);
        if (fileValidationResult.doesValidationFailed()) {
            return FileUploadResult.builder()
                    .message(fileValidationResult.validationMessage())
                    .build();
        }
        return getFileUploadResult(image, imageUploadRequest.fileUploadDirectory());
    }

    public FileDeleteResult deleteFile(FileDeleteRequest fileDeleteRequest) {
        var resultMessage = fileStorageService.deleteFile(fileDeleteRequest.fileName(), fileDeleteRequest.folderDirectory());
        return new FileDeleteResult(resultMessage);
    }

    public FileDeleteResult deleteDirectory(DirectoryDeleteRequest directoryDeleteRequest) {
        var resultMessage = fileStorageService.deleteDirectory(directoryDeleteRequest.directory());
        return new FileDeleteResult(resultMessage);
    }

    private FileUploadResult getFileUploadResult(MultipartFile image, String fileUploadDirectory) {
        var newFileName = FileNameGenerator.generateFileName(image.getOriginalFilename());
        var resultMessage = fileStorageService.uploadFile(image, newFileName, fileUploadDirectory);
        return FileUploadResult.builder()
                .fileName(newFileName)
                .folderDirectory(fileUploadDirectory)
                .message(resultMessage)
                .build();
    }
}
