package com.surikat.docs.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surikat.docs.common.exception.*;
import com.surikat.docs.common.model.rest.ApiError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientResponseException;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

public abstract class AbstractApiController {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error("handleMethodArgumentNotValidException (ErrorCode.BAD_REQUEST)", e);
        List<ObjectError> errors = e.getAllErrors();
        BadArgumentException tmp = new BadArgumentException(e.getMessage(), e);
        if (errors == null || errors.isEmpty()) {
            return new ApiError(tmp.getErrorCode(), tmp.getDescription());
        }
        StringBuilder sb = new StringBuilder();
        for (ObjectError error: errors) {
            if (error.getArguments() != null) {
                for (Object arg: error.getArguments()) {
                    if (arg instanceof DefaultMessageSourceResolvable) {
                        DefaultMessageSourceResolvable argument = (DefaultMessageSourceResolvable) arg;
                        sb.append(argument.getDefaultMessage()).append(" ").append(error.getDefaultMessage()).append("; ");
                    }
                }
            }
        }
        String message = sb.length() == 0 ? tmp.getDescription() : sb.toString();
        return new ApiError(tmp.getErrorCode(), message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error("handleHttpMessageNotReadableException", e);
        HttpMessageParsingException tmp = new HttpMessageParsingException(e.getMessage(), e);
        return new ApiError(tmp.getErrorCode(), tmp.getDescription());
    }

    @ExceptionHandler(DocsService400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleDocsService400Exception(DocsService400Exception e) {
        LOGGER.error("handleDocsService400Exception", e);
        return new ApiError(e.getErrorCode(), e.getDescription());
    }

    @ExceptionHandler(DocsService500Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleDocsService500Exception(DocsService500Exception e) {
        LOGGER.error("handleDocsService500Exception", e);
        return new ApiError(e.getErrorCode(), e.getDescription());
    }

    @ExceptionHandler(MicroserviceMethodErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ApiError> handleMicroserviceMethodErrorException(MicroserviceMethodErrorException e) {
        LOGGER.error("MicroserviceMethodErrorException", e);
        try {
            if (e.getCause() instanceof RestClientResponseException) {
                RestClientResponseException exResp = (RestClientResponseException) e.getCause();
                ApiError apiError = new ObjectMapper().readValue(exResp.getResponseBodyAsString(), ApiError.class);
                String pref = e.getServiceName() != null ? e.getServiceName() + ": " : "";
                ApiError apiErrorResponse = new ApiError(e.getErrorCode(), pref + apiError.getDescription());
                return ResponseEntity.status(exResp.getRawStatusCode()).body(apiErrorResponse);
            }
        } catch (Exception ex) {
            LOGGER.error("Error on handleMicroserviceMethodErrorException", ex);
        }
        return ResponseEntity.status(e.getHttpStatus()).body(new ApiError(e.getErrorCode(), e.getMessage()));
    }

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
