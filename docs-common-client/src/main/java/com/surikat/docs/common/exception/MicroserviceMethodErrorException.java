package com.surikat.docs.common.exception;

import org.springframework.util.StringUtils;

import static com.surikat.docs.common.exception.DocsServiceException.ErrorCode.MICROSERVICE_METHOD_ERROR;

public class MicroserviceMethodErrorException extends DocsService500Exception {

    private String serviceName;

    public MicroserviceMethodErrorException(String msg) {
        this(msg, null);
    }

    public MicroserviceMethodErrorException(String msg, Throwable e) {
        super(MICROSERVICE_METHOD_ERROR, msg, e);
    }

    public MicroserviceMethodErrorException(String msg, String serviceName, Throwable e) {
        super(MICROSERVICE_METHOD_ERROR, msg, e);
        this.serviceName = serviceName;
    }

    public MicroserviceMethodErrorException(String msg, String serviceName, Integer httpStatus, Throwable e) {
        super(MICROSERVICE_METHOD_ERROR, msg, e);
        this.serviceName = serviceName;
        setHttpStatus(httpStatus);
    }

    @Override
    public String getDescription() {
        if (!StringUtils.hasText(serviceName)) {
            return getCode().getDescription().replace(" %s", "");
        }
        return String.format(getCode().getDescription(), serviceName);
    }

    public String getServiceName() {
        return serviceName;
    }
}
