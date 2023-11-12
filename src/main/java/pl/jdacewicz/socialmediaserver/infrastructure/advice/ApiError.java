package pl.jdacewicz.socialmediaserver.infrastructure.advice;

public record ApiError(int errorCode,
                       String message) {
}
