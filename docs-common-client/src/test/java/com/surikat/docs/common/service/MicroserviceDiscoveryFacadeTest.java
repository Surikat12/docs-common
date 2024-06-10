package com.surikat.docs.common.service;

import com.surikat.docs.common.model.MicroserviceSetting;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MicroserviceDiscoveryFacadeTest {

    private final ConfigService configService;
    private final MicroserviceDiscoveryFacade underTest;

    MicroserviceDiscoveryFacadeTest() {
        this.configService = Mockito.mock(ConfigService.class);
        this.underTest = new MicroserviceDiscoveryFacade(configService);
    }

    @Test
    void composeWithoutParamsTest() {
        when(configService
                .getOrDefault(MicroserviceSetting.MICROSERVICE_DOCS_NOTIFY_SERVICE.getConfigPathName(), null))
                .thenReturn("http://test/");

        String url = underTest.compose(MicroserviceSetting.MICROSERVICE_DOCS_NOTIFY_SERVICE, "test");
        assertEquals(url, "http://test/test");
    }

    @Test
    void composeWithParamsTest() {
        when(configService
                .getOrDefault(MicroserviceSetting.MICROSERVICE_DOCS_NOTIFY_SERVICE.getConfigPathName(), null))
                .thenReturn("http://test/");

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("p1", "v1");
        params.put("p2", 42);
        params.put("p3", true);
        params.put("p4", Arrays.asList("lv1", 20, null));
        params.put("p5", new Object[] {"av1", 30, null});
        params.put("p6", null);

        String url = underTest.compose(MicroserviceSetting.MICROSERVICE_DOCS_NOTIFY_SERVICE, "test", params);
        assertEquals(url, "http://test/test?p1=v1&p2=42&p3=true&p4=lv1&p4=20&p5=av1&p5=30");
    }
}