package pl.jdacewicz.socialmediaserver.filestorage;

record FileValidationResult(String validationMessage) {

    private static final String VALIDATION_SUCCESS_MESSAGE = "Validated successfully";

    static FileValidationResult validationSuccess() {
        return new FileValidationResult(VALIDATION_SUCCESS_MESSAGE);
    }

    static FileValidationResult validationFailed(String message) {
        return new FileValidationResult(message);
    }

    boolean doesValidationFailed() {
        return !validationMessage.equals(VALIDATION_SUCCESS_MESSAGE);
    }
}
