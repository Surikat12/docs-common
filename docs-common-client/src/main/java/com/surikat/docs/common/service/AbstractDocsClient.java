package com.surikat.docs.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surikat.docs.common.exception.DocsServiceException;
import com.surikat.docs.common.exception.MicroserviceMethodErrorException;
import com.surikat.docs.common.model.MicroserviceSetting;
import com.surikat.docs.common.model.rest.ApiError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.text.MessageFormat;
import java.util.function.Supplier;

public abstract class AbstractDocsClient {

    private static final Logger INNER_LOGGER = LogManager.getLogger();

    protected abstract MicroserviceSetting getMS();
    protected abstract Logger getLogger();


    protected <Response> Response handleExchange(String url, String methodName, Supplier<ResponseEntity<Response>> exchange) throws MicroserviceMethodErrorException {
        try {
            INNER_LOGGER.info("Exchange with url={} started by {} method", url, methodName);
            ResponseEntity<Response> result = exchange.get();
            if (result.getStatusCode().is2xxSuccessful()) {
                return result.getBody();
            } else {
                throw new MicroserviceMethodErrorException(MessageFormat.format("{0} {1} {2} return error", getMS().name(), url, methodName));
            }
        } catch (RestClientException | MicroserviceMethodErrorException ex) {
            handleException(ex);
        }
        return null;
    }

    protected void handleException(Exception ex) throws MicroserviceMethodErrorException {
        ApiError apiError = getApiError(ex);
        String msg = getMS().name() + ": " + (apiError != null ? apiError.getDescription() : ex.toString());
        throw new MicroserviceMethodErrorException(msg, getMS().name(), getHttpStatus(ex), ex);
    }

    protected int getHttpStatus(Exception ex) {
        if (ex instanceof RestClientResponseException) {
            return ((RestClientResponseException)ex).getRawStatusCode();
        } else {
            return ex instanceof DocsServiceException ? ((DocsServiceException)ex).getHttpStatus() : 500;
        }
    }

    @Nullable
    protected ApiError getApiError(Exception ex) {
        ApiError result = null;
        try {
            if (ex instanceof RestClientResponseException) {
                RestClientResponseException exResp = (RestClientResponseException) ex;
                result = new ObjectMapper().readValue(exResp.getResponseBodyAsString(), ApiError.class);
            }
        } catch (Exception ignored) {
            getLogger().error("Error getting ApiError from ResponseBody", ex);
        }
        return result;
    }
}
