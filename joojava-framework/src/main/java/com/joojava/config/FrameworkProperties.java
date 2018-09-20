package com.joojava.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author leegive
 * 属性配置类
 */
@ConfigurationProperties(prefix = "framework", ignoreUnknownFields = false)
@PropertySources({
    @PropertySource(value = "classpath:git.properties", ignoreResourceNotFound = true),
    @PropertySource(value = "classpath:META-INF/build-info.properties", ignoreResourceNotFound = true)
})
@Getter
public class FrameworkProperties {

    private final Async async = new Async();
    private final Http http = new Http();
    private final Cache cache = new Cache();
    private final Mail mail = new Mail();
    private final Security security = new Security();
    private final Swagger swagger = new Swagger();
    private final Metrics metrics = new Metrics();
    private final Logging logging = new Logging();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final Social social = new Social();
    private final Gateway gateway = new Gateway();
    private final Registry registry = new Registry();

    @Getter @Setter
    public static class Async {
        private int corePoolSize = FrameworkDefaults.Async.corePoolSize;
        private int maxPoolSize = FrameworkDefaults.Async.maxPoolSize;
        private int queueCapacity = FrameworkDefaults.Async.queueCapacity;
    }

    public static class Http {
        public enum Version {V_1_1, V_2_0}

        @Getter
        private final Cache cache = new Cache();

        @Getter @Setter
        private boolean useUndertowUserCipherSuitesOrder = FrameworkDefaults.Http.useUndertowUserCipherSuitesOrder;

        @Getter @Setter
        private Version version = FrameworkDefaults.Http.version;

        @Getter @Setter
        public static class Cache {
            private int timeToLiveInDays = FrameworkDefaults.Http.Cache.timeToLiveInDays;
        }

    }

    @Getter
    public static class Cache {
        private final Hazelcast hazelcast = new Hazelcast();
        private final Ehcache ehcache = new Ehcache();
        private final Infinispan infinispan = new Infinispan();
        private final Memcached memcached = new Memcached();

        public static class Hazelcast {
            @Getter @Setter
            private int timeToLiveSeconds = FrameworkDefaults.Cache.Hazelcast.timeToLiveSeconds;
            @Getter @Setter
            private int backupCount = FrameworkDefaults.Cache.Hazelcast.backupCount;

            @Getter
            private final ManagementCenter managementCenter = new ManagementCenter();

            @Getter @Setter
            public static class ManagementCenter {
                private boolean enabled = FrameworkDefaults.Cache.Hazelcast.ManagementCenter.enabled;
                private int updateInterval = FrameworkDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;
                private String url =  FrameworkDefaults.Cache.Hazelcast.ManagementCenter.url;
            }
        }

        @Getter @Setter
        public static class Ehcache {
            private int timeToLiveSeconds = FrameworkDefaults.Cache.Ehcache.timeToLiveSeconds;
            private long maxEntries = FrameworkDefaults.Cache.Ehcache.maxEntries;
        }

        public static class Infinispan {

            @Getter @Setter
            private String configFile = FrameworkDefaults.Cache.Infinispan.configFile;
            @Getter @Setter
            private boolean statsEnabled = FrameworkDefaults.Cache.Infinispan.statsEnabled;

            @Getter
            private final Local local = new Local();
            @Getter
            private final Distributed distributed = new Distributed();
            @Getter
            private final Replicated replicated = new Replicated();

            @Getter @Setter
            public static class Local {
                private long timeToLiveSeconds = FrameworkDefaults.Cache.Infinispan.Local.timeToLiveSeconds;
                private long maxEntries = FrameworkDefaults.Cache.Infinispan.Local.maxEntries;
            }

            @Getter @Setter
            public static class Distributed {
                private long timeToLiveSeconds = FrameworkDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;
                private long maxEntries = FrameworkDefaults.Cache.Infinispan.Distributed.maxEntries;
                private int instanceCount = FrameworkDefaults.Cache.Infinispan.Distributed.instanceCount;
            }

            @Getter @Setter
            public static class Replicated {
                private long timeToLiveSeconds = FrameworkDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;
                private long maxEntries = FrameworkDefaults.Cache.Infinispan.Replicated.maxEntries;
            }
        }

        @Getter @Setter
        public static class Memcached {
            private boolean enabled = FrameworkDefaults.Cache.Memcached.enabled;
            /**
             * 逗号或空白分隔的服务器地址列表
             */
            private String servers = FrameworkDefaults.Cache.Memcached.servers;
            private int expiration = FrameworkDefaults.Cache.Memcached.expiration;
            private boolean useBinaryProtocol = FrameworkDefaults.Cache.Memcached.useBinaryProtocol;
        }
    }

    @Getter @Setter
    public static class Mail {
        private boolean enabled = FrameworkDefaults.Mail.enabled;
        private String from = FrameworkDefaults.Mail.from;
        private String baseUrl = FrameworkDefaults.Mail.baseUrl;
    }

    @Getter
    public static class Security {
        private final ClientAuthorization clientAuthorization = new ClientAuthorization();
        private final Authentication authentication = new Authentication();
        private final RememberMe rememberMe = new RememberMe();

        @Getter @Setter
        public static class ClientAuthorization {
            private String accessTokenUri = FrameworkDefaults.Security.ClientAuthorization.accessTokenUri;
            private String tokenServiceId = FrameworkDefaults.Security.ClientAuthorization.tokenServiceId;
            private String clientId = FrameworkDefaults.Security.ClientAuthorization.clientId;
            private String clientSecret = FrameworkDefaults.Security.ClientAuthorization.clientSecret;
        }

        @Getter
        public static class Authentication {
            private final Jwt jwt = new Jwt();

            @Getter @Setter
            public static class Jwt {
                private String secret = FrameworkDefaults.Security.Authentication.Jwt.secret;
                private String base64Secret = FrameworkDefaults.Security.Authentication.Jwt.base64Secret;
                private long tokenValidityInSeconds = FrameworkDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe = FrameworkDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;
            }
        }

        @Getter @Setter
        public static class RememberMe {
            @NotNull
            private String key = FrameworkDefaults.Security.RememberMe.key;
        }
    }

    @Getter @Setter
    public static class Swagger {
        private String title = FrameworkDefaults.Swagger.title;
        private String description = FrameworkDefaults.Swagger.description;
        private String version = FrameworkDefaults.Swagger.version;
        private String termsOfServiceUrl = FrameworkDefaults.Swagger.termsOfServiceUrl;
        private String contactName = FrameworkDefaults.Swagger.contactName;
        private String contactUrl = FrameworkDefaults.Swagger.contactUrl;
        private String contactEmail = FrameworkDefaults.Swagger.contactEmail;
        private String license = FrameworkDefaults.Swagger.license;
        private String licenseUrl = FrameworkDefaults.Swagger.licenseUrl;
        private String defaultIncludePattern = FrameworkDefaults.Swagger.defaultIncludePattern;
        private String host = FrameworkDefaults.Swagger.host;
        private String[] protocols = FrameworkDefaults.Swagger.protocols;
        private boolean useDefaultResponseMessages = FrameworkDefaults.Swagger.useDefaultResponseMessages;
    }

    @Getter
    public static class Metrics {
        private final Jmx jmx = new Jmx();
        private final Logs logs = new Logs();

        @Getter @Setter
        public static class Jmx {
            private boolean enabled = FrameworkDefaults.Metrics.Jmx.enabled;
        }

        @Getter @Setter
        public static class Logs {
            private boolean enabled = FrameworkDefaults.Metrics.Logs.enabled;
            private long reportFrequency = FrameworkDefaults.Metrics.Logs.reportFrequency;
        }
    }

    @Getter
    public static class Logging {
        private final Logstash logstash = new Logstash();

        @Getter @Setter
        public static class Logstash {
            private boolean enabled = FrameworkDefaults.Logging.Logstash.enabled;
            private String host = FrameworkDefaults.Logging.Logstash.host;
            private int port = FrameworkDefaults.Logging.Logstash.port;
            private int queueSize = FrameworkDefaults.Logging.Logstash.queueSize;
        }
    }

    @Getter @Setter
    public static class Social {
        private String redirectAfterSignIn = FrameworkDefaults.Social.redirectAfterSignIn;
    }

    public static class Gateway {

        @Getter
        private final RateLimiting rateLimiting = new RateLimiting();

        @Getter @Setter
        private Map<String, List<String>> authorizedMicroservicesEndpoints = FrameworkDefaults.Gateway.authorizedMicroservicesEndpoints;

        @Getter @Setter
        public static class RateLimiting {
            private boolean enabled = FrameworkDefaults.Gateway.RateLimiting.enabled;
            private long limit = FrameworkDefaults.Gateway.RateLimiting.limit;
            private int durationInSeconds = FrameworkDefaults.Gateway.RateLimiting.durationInSeconds;
        }
    }

    @Getter @Setter
    public static class Registry {
        private String password = FrameworkDefaults.Registry.password;
    }

}
