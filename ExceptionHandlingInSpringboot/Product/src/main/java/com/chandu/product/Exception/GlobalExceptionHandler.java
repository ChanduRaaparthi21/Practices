package com.chandu.product.Exception;

import com.chandu.product.Entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> ProductNotFoundException(ProductNotFoundException pnfc, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(pnfc.getMessage(), webRequest.getDescription(false),"PRODUCT_NOT_FOUND");
        return new ResponseEntity<> (errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateProductFoundExcception.class)
    public ResponseEntity<ErrorResponse> DuplicateProductFoundExcception(DuplicateProductFoundExcception dpfe, WebRequest webRequest) {
       ErrorResponse errorResponse = new ErrorResponse(dpfe.getMessage(), webRequest.getDescription(false),"DUPLICATE_PRODUCT_FOUND");
       return new ResponseEntity<> (errorResponse, HttpStatus.CONFLICT);
    }

}
