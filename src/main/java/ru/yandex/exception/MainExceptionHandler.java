package ru.yandex.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class MainExceptionHandler {
    @ExceptionHandler
    public Map<String, String> handlerUserNotFoundException(UserNotFoundException ex) {
        return Map.of("errorCode", "404", "errorMessage", "NOT_FOUND", "reason", ex.getMessage());
    }
    @ExceptionHandler
    public Map<String, String> handlerFilmNotFoundException(FilmNotFoundException ex) {
        return Map.of("errorCode", "404", "errorMessage", "NOT_FOUND", "reason", ex.getMessage());
    }
    @ExceptionHandler
    public Map<String, String> handlerValidationException(ValidationException ex) {
        return Map.of("errorCode", "400", "errorMessage", "BAD_REQUEST", "reason", ex.getMessage());
    }

    @ExceptionHandler
    public Map<String, String> handlerException(Exception ex) {
        return Map.of("errorCode", "500", "errorMessage", "INTERNAL_SERVER_ERROR", "reason", ex.getMessage());
    }
}
