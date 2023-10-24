package pl.jdacewicz.socialmediaserver.filestorage;

import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.jdacewicz.socialmediaserver.filestorage.FileValidationResult.validationFailed;
import static pl.jdacewicz.socialmediaserver.filestorage.FileValidationResult.validationSuccess;
import static pl.jdacewicz.socialmediaserver.filestorage.ValidationError.*;

class ImageValidator {

    private final List<String> SUPPORTED_CONTENT_TYPES = List.of("image/png", "image/jpg", "image/jpeg");

    private List<ValidationError> errors = new LinkedList<>();

    public FileValidationResult validate(MultipartFile file) {
        if (file.isEmpty()) {
            errors.add(EMPTY);
        }
        if (!doesFileHasExtension(file.getOriginalFilename())) {
            errors.add(MISSING_EXTENSION);
        }
        if (!isSupportedContentType(file.getContentType())) {
            errors.add(NOT_SUPPORTED_CONTENT_TYPE);
        }
        if (errors.isEmpty()) {
            validationSuccess();
        }
        String message = concatValidationErrors();
        return validationFailed(message);
    }

    private boolean isSupportedContentType(String contentType) {
        return SUPPORTED_CONTENT_TYPES.stream()
                .anyMatch(supportedType -> supportedType.equals(contentType));
    }


    private boolean doesFileHasExtension(String originalFileName) {
        return (originalFileName != null && originalFileName.lastIndexOf('.') >= 0);
    }

    private String concatValidationErrors() {
        var determiner = ",";
        return errors.stream()
                .map(error -> error.message)
                .collect(Collectors.joining(determiner));
    }
}
