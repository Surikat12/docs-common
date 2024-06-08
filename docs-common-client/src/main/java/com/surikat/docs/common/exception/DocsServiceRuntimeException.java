package com.surikat.docs.common.exception;

public class DocsServiceRuntimeException extends RuntimeException {

    private final Code code;
    private Integer httpStatus;

    public DocsServiceRuntimeException() {
        this(DocsServiceException.ErrorCode.SERVER_ERROR);
    }

    public DocsServiceRuntimeException(Code code) {
        super();
        this.code = code;
    }

    public DocsServiceRuntimeException(Code code, String msg) {
        super(msg);
        this.code = code;
    }

    public DocsServiceRuntimeException(Code code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public DocsServiceRuntimeException(String msg) {
        this(DocsServiceException.ErrorCode.SERVER_ERROR, msg);
    }

    public DocsServiceRuntimeException(String msg, Throwable e) {
        this(DocsServiceException.ErrorCode.SERVER_ERROR, msg, e);
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Code getCode() {
        return code;
    }

    public int getErrorCode() {
        return code.getValue();
    }

    public String getDescription() {
        return this.getMessage() != null ? this.getMessage() : code.getDescription();
    }

    public enum ErrorRuntimeCode implements Code {
        SERVER_ERROR(1, "На сервере произошла ошибка");

        private final int value;
        private final String description;

        ErrorRuntimeCode(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
