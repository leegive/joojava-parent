package com.joojava.config.apidoc.customizer;


import com.joojava.config.FrameworkProperties;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author leegive
 *
 * 配置Swagger
 */
@RequiredArgsConstructor
public class DefaultSwaggerCustomizer implements SwaggerCustomizer, Ordered {

    public static final int DEFAULT_ORDER = 0;

    @Setter
    private int order = DEFAULT_ORDER;

    private final FrameworkProperties.Swagger properties;

    @Override
    public void customize(Docket docket) {

        Contact contact = new Contact(
            properties.getContactName(),
            properties.getContactUrl(),
            properties.getContactEmail()
        );

        ApiInfo apiInfo = new ApiInfo(
            properties.getTitle(),
            properties.getDescription(),
            properties.getVersion(),
            properties.getTermsOfServiceUrl(),
            contact,
            properties.getLicense(),
            properties.getLicenseUrl(),
            new ArrayList<>()
        );

        docket.host(properties.getHost())
            .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
            .apiInfo(apiInfo)
            .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
            .forCodeGeneration(true)
            .directModelSubstitute(ByteBuffer.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .select()
            .paths(regex(properties.getDefaultIncludePattern()))
            .build();

    }

    @Override
    public int getOrder() {
        return order;
    }
}
