package com.surikat.docs.common.exception;

import static com.surikat.docs.common.exception.DocsServiceException.ErrorCode.DATA_BASE_ERROR;

public class DataBaseException extends DocsService500Exception {

    public DataBaseException() {
        super(DATA_BASE_ERROR);
    }

    public DataBaseException(String msg) {
        super(DATA_BASE_ERROR, msg);
    }

    public DataBaseException(String msg, Throwable e) {
        super(DATA_BASE_ERROR, msg, e);
    }
}
