package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity handleExpiredJwtException(ExpiredJwtException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Token已過期，請重新登入");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleJwtException(MalformedJwtException ex) {
        // 返回 401 状态码和错误信息
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
    }

}
