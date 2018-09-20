package com.joojava.config.apidoc.customizer;

import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author leegive
 * Callback 自定义配置接口
 */
public interface SwaggerCustomizer {

    /**
     * 配置Docket
     * @param docket
     */
    void customize(Docket docket);

}
