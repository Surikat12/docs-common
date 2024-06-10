package com.surikat.docs.common.exception;

public class DocsService400Exception extends DocsServiceException {

    public DocsService400Exception(Code code, String msg) {
        super(code, msg, null);
    }

    public DocsService400Exception(Code code, String msg, Throwable e) {
        super(code, msg, e);
        setHttpStatus(400);
    }
}
