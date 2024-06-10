package com.surikat.docs.common.exception;

public class DocsService500Exception extends DocsServiceException {

    public DocsService500Exception(Code code, String msg, Throwable e) {
        super(code, msg, e);
        setHttpStatus(500);
    }

    public DocsService500Exception(Code code, String msg) {
        this(code, msg, null);
    }

    public DocsService500Exception(Code code) {
        this(code, null, null);
    }
}
