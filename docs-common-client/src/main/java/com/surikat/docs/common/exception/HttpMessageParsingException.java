package com.surikat.docs.common.exception;

import static com.surikat.docs.common.exception.DocsServiceException.ErrorCode.BAD_REQUEST;

public class HttpMessageParsingException extends DocsService400Exception {

    public HttpMessageParsingException(String msg) {
        super(BAD_REQUEST, msg);
    }
    public HttpMessageParsingException(String msg, Throwable e) {
        super(BAD_REQUEST, msg, e);
    }
}
