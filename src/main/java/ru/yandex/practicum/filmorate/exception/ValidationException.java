package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("Ошибка при создании, ошибка в: " + message);
    }
}
