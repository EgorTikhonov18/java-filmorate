package ru.yandex.practicum.filmorate.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

import javax.validation.ValidationException;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final FilmNotFoundException  e) {
        return new ErrorResponse(
                 e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse jsonHandle(final FilmReleaseException  e) {
        return new ErrorResponse( e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse jsonHandle(final ValidationException e) {
        return new ErrorResponse(e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse parsHandle(final NumberFormatException e) {
        return new ErrorResponse(
                 e.getMessage()
        );
    }
}