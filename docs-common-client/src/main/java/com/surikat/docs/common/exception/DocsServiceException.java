package com.surikat.docs.common.exception;

public class DocsServiceException extends Exception {

    private final Code code;
    private Integer httpStatus;

    public DocsServiceException() {
        this(ErrorCode.SERVER_ERROR);
    }

    public DocsServiceException(Code code) {
        super();
        this.code = code;
    }

    public DocsServiceException(Code code, String msg) {
        super(msg);
        this.code = code;
    }

    public DocsServiceException(Code code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public DocsServiceException(String msg) {
        this(ErrorCode.SERVER_ERROR, msg);
    }

    public DocsServiceException(String msg, Throwable e) {
        this(ErrorCode.SERVER_ERROR, msg, e);
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

    public enum ErrorCode implements Code {
        SERVER_ERROR(1, "На сервере произошла ошибка"),
        BAD_REQUEST(2, "Неверные параметры запроса (%s)"),

        MICROSERVICE_METHOD_ERROR(50, "Микросервис %s вернул ошибку"),

        DATA_BASE_ERROR(70, "Ошибка в работе базы данных");

        private final int value;
        private final String description;

        ErrorCode(int value, String description) {
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