package com.surikat.docs.common.model;

public enum MicroserviceSetting {
    MICROSERVICE_DOCS_NOTIFY_SERVICE("microservice.docs-notify-service.path");

    private final String configPathName;

    MicroserviceSetting(String configPathName) {
        this.configPathName = configPathName;
    }

    public String getConfigPathName() {
        return configPathName;
    }
}
