package com.surikat.docs.common.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class ConfigServiceImpl implements ConfigService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Environment env;
    private final Map<String, String> configuration;

    public ConfigServiceImpl(Environment env) {
        this.env = env;
        configuration = new HashMap<String, String>();
    }

    @Override
    public String getOrDefault(String name, String defaultValue) {
        return configuration.computeIfAbsent(name, k -> {
            if (env.containsProperty(name)) {
                return env.getProperty(name);
            } else {
                LOGGER.info("Not found configuration for property {}", k);
                return defaultValue;
            }
        });
    }

    @Override
    public void setParameter(String key, String value) {
        configuration.put(key, value);
    }

    @Override
    public Integer getInteger(String name) {
        try {
            return Integer.parseInt(configuration.get(name));
        } catch (NumberFormatException ex) {
            LOGGER.error("result param (code='{}', value='{}') convert to integer error",
                    name, configuration.get(name));
        }
        return null;
    }
}
