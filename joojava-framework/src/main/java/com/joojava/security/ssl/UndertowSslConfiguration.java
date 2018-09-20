package com.joojava.security.ssl;

import com.joojava.config.FrameworkProperties;
import io.undertow.UndertowOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author leegive
 */
@Configuration
@ConditionalOnClass({UndertowServletWebServerFactory.class})
@ConditionalOnProperty({"server.ssl.ciphers", "server.ssl.key-store"})
@Slf4j
public class UndertowSslConfiguration {

    private final UndertowServletWebServerFactory factory;

    private final FrameworkProperties frameworkProperties;

    public UndertowSslConfiguration(UndertowServletWebServerFactory undertowServletWebServerFactory, FrameworkProperties frameworkProperties) {
        this.factory = undertowServletWebServerFactory;
        this.frameworkProperties = frameworkProperties;

        configuringUserCipherSuiteOrder();
    }

    private void configuringUserCipherSuiteOrder() {
        log.info("Configuring Undertow");
        if (frameworkProperties.getHttp().isUseUndertowUserCipherSuitesOrder()) {
            log.info("Setting user cipher suite order to true");
            factory.addBuilderCustomizers(builder -> builder.setSocketOption(UndertowOptions.SSL_USER_CIPHER_SUITES_ORDER, Boolean.TRUE));
        }
    }

}
