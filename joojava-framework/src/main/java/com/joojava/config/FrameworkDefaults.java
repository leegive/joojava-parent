package com.joojava.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leegive
 * 默认配置项
 */
public interface FrameworkDefaults {

    interface Async {
        int corePoolSize = 2;
        int maxPoolSize = 50;
        int queueCapacity = 10000;
    }

    interface Http {
        FrameworkProperties.Http.Version version = FrameworkProperties.Http.Version.V_1_1;
        boolean useUndertowUserCipherSuitesOrder = true;

        interface Cache {
            int timeToLiveInDays = 1461;
        }
    }

    interface Cache {

        interface Hazelcast {
            int timeToLiveSeconds = 3600;
            int backupCount = 1;

            interface ManagementCenter {
                boolean enabled = false;
                int updateInterval = 3;
                String url = "";
            }
        }

        interface Ehcache {
            int timeToLiveSeconds = 3600;
            long maxEntries = 100;
        }

        interface Infinispan {
            String configFile = "default-configs/default-jgroups-tcp.xml";
            boolean statsEnabled = false;

            interface Local {
                long timeToLiveSeconds = 60;
                long maxEntries = 100;
            }

            interface Distributed {
                long timeToLiveSeconds = 60;
                long maxEntries = 100;
                int instanceCount = 1;
            }

            interface Replicated {
                long timeToLiveSeconds = 60;
                long maxEntries = 100;
            }
        }

        interface Memcached {
            boolean enabled = false;
            String servers = "localhost:11211";
            int expiration = 300;
            boolean useBinaryProtocol = true;
        }
    }

    interface Mail {
        boolean enabled = false;
        String from = "";
        String baseUrl = "";
    }

    interface Security {
        interface ClientAuthorization {
            String accessTokenUri = null;
            String tokenServiceId = null;
            String clientId = null;
            String clientSecret = null;
        }

        interface Authentication {
            interface Jwt {
                String secret = null;
                String base64Secret = null;
                long tokenValidityInSeconds = 1800;
                long tokenValidityInSecondsForRememberMe = 2592000;
            }
        }

        interface RememberMe {
            String key = null;
        }
    }

    interface Swagger {
        String title = "Application API";
        String description = "API documentation";
        String version = "0.0.1";
        String termsOfServiceUrl = null;
        String contactName = null;
        String contactUrl = null;
        String contactEmail = null;
        String license = null;
        String licenseUrl = null;
        String defaultIncludePattern = "/api/.*";
        String host = null;
        String[] protocols = {};
        boolean useDefaultResponseMessages = true;
    }


    interface Metrics {
        interface Jmx {
            boolean enabled = true;
        }

        interface Logs {
            boolean enabled = false;
            long reportFrequency = 60;
        }
    }

    interface Logging {
        interface Logstash {
            boolean enabled = false;
            String host = "localhost";
            int port = 5000;
            int queueSize = 512;
        }
    }

    interface Social {
        String redirectAfterSignIn = "/#/home";
    }

    interface Gateway {
        Map<String, List<String>> authorizedMicroservicesEndpoints = new LinkedHashMap<>();

        interface RateLimiting {
            boolean enabled = false;
            long limit = 100_000L;
            int durationInSeconds = 3_600;
        }
    }

    interface Ribbon {
        String[] displayOnActiveProfiles = null;
    }

    interface Registry {
        String password = null;
    }


}
