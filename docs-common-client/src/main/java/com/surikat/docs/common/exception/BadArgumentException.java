package com.surikat.docs.common.exception;

import org.springframework.util.StringUtils;

import static com.surikat.docs.common.exception.DocsServiceException.ErrorCode.BAD_REQUEST;

public class BadArgumentException extends DocsService400Exception {

    private String addInfo;

    public BadArgumentException(String msg) {
        super(BAD_REQUEST, msg);
    }

    public BadArgumentException(String msg, Throwable e) {
        super(BAD_REQUEST, msg, e);
    }

    public BadArgumentException(String msg, String addInfo) {
        this(msg);
        this.addInfo = addInfo;
    }

    @Override
    public String getDescription() {
        if (StringUtils.hasText(addInfo)) {
            return getCode().getDescription().replace("(%s)", "");
        }
        return String.format(getCode().getDescription(), addInfo);
    }
}
