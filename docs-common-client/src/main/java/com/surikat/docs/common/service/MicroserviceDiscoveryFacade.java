package com.surikat.docs.common.service;

import com.surikat.docs.common.model.MicroserviceSetting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MicroserviceDiscoveryFacade {

    private static final Logger LOGGER = LogManager.getLogger();
    private final ConfigService configService;

    public MicroserviceDiscoveryFacade(ConfigService configService) {
        this.configService = configService;
    }

    public HttpEntity<String> getDefaultEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }

    public String compose(MicroserviceSetting microservice, String path) {
        return compose(microservice, path, null);
    }

    public String compose(MicroserviceSetting microservice, String path, Map<String, ?> params) {
        String result = configService.getOrDefault(microservice.getConfigPathName(), null);
        if (path == null) {
            return result;
        }
        result = result.endsWith("/") ? result : result + '/';
        result += path.startsWith("/") ? path.substring(1) : path;

        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder("?");

            for (String param : params.keySet()) {
                Object value = params.get(param);
                if (value != null) {
                    if (isParamValueCollection(value)) {
                        toValueList(value).forEach(paramValue -> sb.append(buildParamKeyValue(param, paramValue)));
                    } else {
                        sb.append(buildParamKeyValue(param, toStringValue(value)));
                    }
                }
            }

            sb.deleteCharAt(sb.length() - 1);
            result += sb.toString();
        }

        return result;
    }

    private boolean isParamValueCollection(Object paramValue) {
        if (paramValue != null) {
            return paramValue.getClass().isArray() || Collection.class.isAssignableFrom(paramValue.getClass());
        }
        return false;
    }

    private List<String> toValueList(Object paramValue) {
        List<String> result = new ArrayList<>();
        if (paramValue.getClass().isArray()) {
            Object[] values = (Object[]) paramValue;
            for (Object value : values) {
                if (value != null) {
                    result.add(toStringValue(value));
                }
            }
        } else if (Collection.class.isAssignableFrom(paramValue.getClass())) {
            Collection collection = (Collection) paramValue;
            for (Object value : collection) {
                if (value != null) {
                    result.add(toStringValue(value));
                }
            }
        }
        return result;
    }

    private String toStringValue(Object param) {
        return param.toString();
    }

    private String buildParamKeyValue(String key, String value) {
        return String.format("%s=%s&", key, value);
    }
}
