package com.joojava.config;

/**
 * @author leegive
 * 常量信息
 */
public interface FrameworkConstants {

    /**
     * 开发
     */
    String SPRING_PROFILE_DEVELOPMENT = "dev";

    /**
     * 测试
     */
    String SPRING_PROFILE_TEST = "test";

    /**
     * 生产
     */
    String SPRING_PROFILE_PRODUCTION = "prod";

    /**
     * 文档
     */
    String SPRING_PROFILE_SWAGGER = "swagger";

    /**
     * liquibase
     */
    String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";

    /**
     * CloudFoundry
     */
    String SPRING_PROFILE_CLOUD = "cloud";

    /**
     * Heroku
     */
    String SPRING_PROFILE_HEROKU = "heroku";

    /**
     * Kubernetes
     */
    String SPRING_PROFILE_K8S = "k8s";


}
