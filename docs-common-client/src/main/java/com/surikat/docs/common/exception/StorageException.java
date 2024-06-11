package com.surikat.docs.common.exception;

import static com.surikat.docs.common.exception.DocsServiceException.ErrorCode.STORAGE_ERROR;

public class StorageException extends DocsService500Exception {

    public StorageException(String msg) {
        this(msg, null);
    }

    public StorageException(String msg, Throwable e) {
        super(STORAGE_ERROR, msg, e);
    }
}
