package com.ahnlab.ti.tools.zkui.exception.handler;

import com.ahnlab.ti.tools.zkui.exception.ErrorResult;
import com.ahnlab.ti.tools.zkui.exception.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.ahnlab.ti.tools.zkui")
public class UserControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult IllegalArgumentException(IllegalArgumentException e){
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(ZnodeNotFoundException.class)
    public ErrorResult ZnodeNotFoundException(ZnodeNotFoundException e){
        log.error("[ZnodeNotFoundException] ex", e);
        return new ErrorResult(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatePathException.class)
    public ErrorResult DuplicatePathException(DuplicatePathException e){
        log.error("[DuplicatePathException] ex", e);
        return new ErrorResult(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResult GlobalException(GlobalException e){
        log.error("[GlobalException] ex", e);
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateNameException.class)
    public ErrorResult DuplicateNameException(DuplicateNameException e){
        log.error("[DuplicateNameException] ex", e);
        return new ErrorResult(HttpStatus.CONFLICT.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(HostNotFoundException.class)
    public ErrorResult HostNotFoundException(HostNotFoundException e){
        log.error("[HostNotFoundException] ex", e);
        return new ErrorResult(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }

}
