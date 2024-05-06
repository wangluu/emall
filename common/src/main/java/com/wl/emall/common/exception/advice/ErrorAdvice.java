package com.wl.emall.common.exception.advice;

import com.wl.emall.common.exception.dto.ErrorResponse;
import com.wl.emall.common.exception.EmallCommonException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = {RestController.class})
public class ErrorAdvice {

    @ResponseBody
    @ExceptionHandler(value = EmallCommonException.class)
    public ResponseEntity<ErrorResponse> errorHandler(EmallCommonException ex) {
        log.info("Business Error:{}.", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getCode(), ex.getMessage()), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> errorHandler(Exception ex) {
        log.error("Error happens.", ex);
        return new ResponseEntity<>(new ErrorResponse("-1", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
