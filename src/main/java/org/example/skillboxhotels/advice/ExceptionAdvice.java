package org.example.skillboxhotels.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.skillboxhotels.exception.BadRequestException;
import org.example.skillboxhotels.exception.ConflictException;
import org.example.skillboxhotels.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDescription exception(Exception e) {
        log.error("Unknown exception", e);
        return new ErrorDescription(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDescription notFound(NotFoundException e) {
        return new ErrorDescription(e);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDescription badRequest(BadRequestException e) {
        return new ErrorDescription(e);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDescription conflict(ConflictException e) {
        return new ErrorDescription(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDescription methodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ErrorDescription(String.format("Validation failed for fields: %s",
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getField() + "(" + fieldError.getDefaultMessage() + ")")
                        .collect(Collectors.joining(","))));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDescription missingRequestHeader(MissingRequestHeaderException e) {
        return new ErrorDescription(e.getMessage());
    }
}