package org.example.advice;


import org.example.dto.ExceptionDTO;
import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ExceptionDTO handle (PasswordNotMatchesException e){
        e.printStackTrace();
        return new ExceptionDTO("item.password_not_matches");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //400
    public ExceptionDTO handle (InvalidDataException e) {
        e.printStackTrace();
        return new ExceptionDTO("item.data_limit_violated");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    public ExceptionDTO handle (NotAuthenticatedException e){
        e.printStackTrace();
        return new ExceptionDTO("item.not_authenticated");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ExceptionDTO handle (NotMakeReviewException e) {
        e.printStackTrace();
        return new ExceptionDTO("you_can_comment_to_authorized_users");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ExceptionDTO handle (ForbiddenException e){
        e.printStackTrace();
        return new ExceptionDTO("item.access_restrictions");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    public ExceptionDTO handle (IOException e){
        e.printStackTrace();
        return new ExceptionDTO("item.io_is_broken");
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    public ExceptionDTO handle (ProductOutOfStockException e){
        e.printStackTrace();
        return  new ExceptionDTO("the_quantity_is_limited");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public ExceptionDTO handle (Exception e) {
        e.printStackTrace();
        return new ExceptionDTO("global.internal_error");
    }
}
