package com.ahnlab.ti.tools.zkui.exception.handler;

import com.ahnlab.ti.tools.zkui.exception.ErrorResult;
import com.ahnlab.ti.tools.zkui.exception.user.DuplicatePathException;
import com.ahnlab.ti.tools.zkui.exception.user.GlobalException;
import com.ahnlab.ti.tools.zkui.exception.user.InvalidPathException;
import com.ahnlab.ti.tools.zkui.exception.user.ZnodeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.ahnlab.ti.tools.zkui")
public class UserControllerAdvice {

    @ExceptionHandler(InvalidPathException.class)
    public ErrorResult InvalidPathException(InvalidPathException e){
        log.error("[InvalidPathException] ex", e);
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    @ExceptionHandler(ZnodeNotFoundException.class)
    public ErrorResult ZnodeNotFoundException(ZnodeNotFoundException e){
        log.error("[ZnodeNotFoundException] ex", e);
        return new ErrorResult(HttpStatus.NO_CONTENT.value(), e.getMessage());
    }
    @ExceptionHandler(DuplicatePathException.class)
    public ErrorResult DuplicatePathException(DuplicatePathException e){
        log.error("[DuplicatePathException] ex", e);
        return new ErrorResult(HttpStatus.CONFLICT.value(), e.getMessage());
    }
    @ExceptionHandler({Exception.class})
    public ErrorResult GlobalException(GlobalException e){
        log.error("[GlobalException] ex", e);
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
