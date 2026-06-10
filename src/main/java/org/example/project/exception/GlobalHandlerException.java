package org.example.project.exception;

import org.example.project.common.enumType.reponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error) -> {

            errors.put(error.getField(), error.getDefaultMessage());
        });
        ApiResponse apiResponse = ApiResponse.builder().success(false).data(null).error(errors).message("FAILER").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException e) {

        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .status(415)
                                .message("Content-Type phải là application/json")
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e) {

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .success(false)
                        .status(400)
                        .message("Request body không được để trống")
                        .build()
        );
    }


    @ExceptionHandler(ValidAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleEmailAlreadyExists(
            ValidAlreadyExistsException e) {

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .success(false)
                        .status(400)
                        .message(e.getMessage())
                        .build()
        );
    }

}
