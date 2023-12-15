package com.example.demo.exception;


// 註冊時已有此帳號的exception
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}
