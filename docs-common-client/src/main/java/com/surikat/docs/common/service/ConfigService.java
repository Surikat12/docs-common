package com.surikat.docs.common.service;

public interface ConfigService {

    String getOrDefault(String name, String defaultValue);
    void setParameter(String key, String value);
    Integer getInteger(String name);
}
