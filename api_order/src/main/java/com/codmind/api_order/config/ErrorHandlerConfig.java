package com.codmind.api_order.config;

import com.codmind.api_order.exceptions.DataServiceException;
import com.codmind.api_order.exceptions.GeneralServiceException;
import com.codmind.api_order.exceptions.ValidateServiceException;
import com.codmind.api_order.utils.WrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> all(Exception e, WebRequest request){
        log.error(e.getMessage(), e);
        WrapperResponse<?> response = new WrapperResponse<>(false, "Internal Server Error", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateServiceException.class)
    public ResponseEntity<?> ValidateServiceException (ValidateServiceException e, WebRequest request){
        log.info(e.getMessage(), e);
        WrapperResponse<?> response = new WrapperResponse<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataServiceException.class)
    public ResponseEntity<?> DataServiceException (DataServiceException e, WebRequest request){
        log.info(e.getMessage(), e);
        WrapperResponse<?> response = new WrapperResponse<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralServiceException.class)
    public ResponseEntity<?> GeneralServiceException (GeneralServiceException e, WebRequest request){
        log.error(e.getMessage(), e);
        WrapperResponse<?> response = new WrapperResponse<>(false,"Internal Server Error", null);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
