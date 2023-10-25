package pl.jdacewicz.socialmediaserver.filestorage;

import lombok.Getter;

@Getter
enum ValidationError {
    IS_NULL("File have to be provided"),
    EMPTY("Provided file cannot be empty"),
    MISSING_EXTENSION("Provided file is missing file extension"),
    NOT_SUPPORTED_CONTENT_TYPE("Provided file has unsupported content type");

    private final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
