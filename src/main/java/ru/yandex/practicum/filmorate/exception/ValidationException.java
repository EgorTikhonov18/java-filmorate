package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends Throwable {
    public ValidationException(final String message) {
        super(message);
    }
}
