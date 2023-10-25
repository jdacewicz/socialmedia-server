package pl.jdacewicz.socialmediaserver.filestorage;

import org.apache.commons.lang.RandomStringUtils;

class FileNameGenerator {

    static String generateFileName(String originalFileName) {
        var extension = extractFileExtension(originalFileName);
        return RandomStringUtils.randomAlphanumeric(16) + extension;
    }

    private static String extractFileExtension(String originalFileName) {
        var lastDotIndex = originalFileName.lastIndexOf('.');
        return (lastDotIndex > -1) ? originalFileName.substring(lastDotIndex) : "";
    }
}
