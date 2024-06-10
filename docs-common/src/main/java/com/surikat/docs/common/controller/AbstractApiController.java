package com.surikat.docs.common.controller;

import com.surikat.docs.common.exception.DocsServiceException;
import com.surikat.docs.common.exception.DocsServiceRuntimeException;
import com.surikat.docs.common.model.rest.ApiError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.UndeclaredThrowableException;

public abstract class AbstractApiController {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(DocsServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleDocsServiceException(DocsServiceException e) {
        LOGGER.error("handleDocsServiceException", e);
        return new ApiError(e.getErrorCode(), e.getDescription());
    }

    @ExceptionHandler(DocsServiceRuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleDocsServiceRuntimeException(DocsServiceRuntimeException e) {
        LOGGER.error("handleDocsServiceRuntimeException", e);
        return new ApiError(e.getErrorCode(), e.getDescription());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleException(Exception e) {
        LOGGER.error("handleException (ErrorCode.SERVER_ERROR)", e);
        return new ApiError(DocsServiceException.ErrorCode.SERVER_ERROR.getValue(), e.toString());
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleException(UndeclaredThrowableException e) {
        LOGGER.error("handleException (ErrorCode.SERVER_ERROR)", e);
        return new ApiError(DocsServiceException.ErrorCode.SERVER_ERROR.getValue(), e.toString());
    }
}
