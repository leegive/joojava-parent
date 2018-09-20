package com.joojava.config;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class FrameworkPropertiesTest {

    private FrameworkProperties properties;

    @Before
    public void setup() {
        properties = new FrameworkProperties();
    }

    @Test
    public void testComplete() throws Exception {
        Set<String> set = new LinkedHashSet<>(64, 1F);
        reflect(properties, set, "test");
        for (String name : set) {
            this.getClass().getDeclaredMethod(name);
        }
    }

    private void reflect(Object obj, Set<String> dst, String prefix) throws Exception {
        Class<?> src = obj.getClass();
        for (Method method : src.getDeclaredMethods()) {
            String name = method.getName();
            if (name.startsWith("get")) {
                Object res = method.invoke(obj, (Object[]) null);
                if (res != null && src.equals(res.getClass().getDeclaredClasses())) {
                    reflect(res, dst, prefix + name.substring(3));
                }
            } else if (name.startsWith("set")) {
                dst.add(prefix + name.substring(3));
            }
        }
    }

    @Test
    public void testAsyncCorePoolSize() {
        FrameworkProperties.Async obj = properties.getAsync();
        int val = FrameworkDefaults.Async.corePoolSize;
        assertThat(obj.getCorePoolSize()).isEqualTo(val);
        val++;
        obj.setCorePoolSize(val);
        assertThat(obj.getCorePoolSize()).isEqualTo(val);
    }

    @Test
    public void testAsyncMaxPoolSize() {
        FrameworkProperties.Async obj = properties.getAsync();
        int val = FrameworkDefaults.Async.maxPoolSize;
        assertThat(obj.getMaxPoolSize()).isEqualTo(val);
        val++;
        obj.setMaxPoolSize(val);
        assertThat(obj.getMaxPoolSize()).isEqualTo(val);
    }

    @Test
    public void testAsyncQueueCapacity() {
        FrameworkProperties.Async obj = properties.getAsync();
        int val = FrameworkDefaults.Async.queueCapacity;
        assertThat(obj.getQueueCapacity()).isEqualTo(val);
        val++;
        obj.setQueueCapacity(val);
        assertThat(obj.getQueueCapacity()).isEqualTo(val);
    }

    @Test
    public void testHttpVersion() {
        FrameworkProperties.Http.Version[] versions = FrameworkProperties.Http.Version.values();
        FrameworkProperties.Http obj = properties.getHttp();
        String str = FrameworkDefaults.Http.version.toString();
        FrameworkProperties.Http.Version val = FrameworkProperties.Http.Version.valueOf(str);
        assertThat(obj.getVersion()).isEqualTo(val);
        val = versions[(1 + val.ordinal()) % versions.length];
        obj.setVersion(val);
        assertThat(obj.getVersion()).isEqualTo(val);
    }

    @Test
    public void testHttpCacheTimeToLiveInDays() {
        FrameworkProperties.Http.Cache obj = properties.getHttp().getCache();
        int val = FrameworkDefaults.Http.Cache.timeToLiveInDays;
        assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
        val++;
        obj.setTimeToLiveInDays(val);
        assertThat(obj.getTimeToLiveInDays()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastTimeToLiveSeconds() {
        FrameworkProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
        int val = FrameworkDefaults.Cache.Hazelcast.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastBackupCount() {
        FrameworkProperties.Cache.Hazelcast obj = properties.getCache().getHazelcast();
        int val = FrameworkDefaults.Cache.Hazelcast.backupCount;
        assertThat(obj.getBackupCount()).isEqualTo(val);
        val++;
        obj.setBackupCount(val);
        assertThat(obj.getBackupCount()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastManagementCenterEnabled() {
        FrameworkProperties.Cache.Hazelcast.ManagementCenter obj =
            properties.getCache().getHazelcast().getManagementCenter();

        boolean val = FrameworkDefaults.Cache.Hazelcast.ManagementCenter.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastManagementCenterUpdateInterval() {
        FrameworkProperties.Cache.Hazelcast.ManagementCenter obj =
            properties.getCache().getHazelcast().getManagementCenter();

        int val = FrameworkDefaults.Cache.Hazelcast.ManagementCenter.updateInterval;
        assertThat(obj.getUpdateInterval()).isEqualTo(val);
        val++;
        obj.setUpdateInterval(val);
        assertThat(obj.getUpdateInterval()).isEqualTo(val);
    }

    @Test
    public void testCacheHazelcastManagementCenterUrl() {
        FrameworkProperties.Cache.Hazelcast.ManagementCenter obj =
            properties.getCache().getHazelcast().getManagementCenter();

        String val = FrameworkDefaults.Cache.Hazelcast.ManagementCenter.url;
        assertThat(obj.getUrl()).isEqualTo(val);
        val = "http://localhost:8080";
        obj.setUrl(val);
        assertThat(obj.getUrl()).isEqualTo(val);
    }

    @Test
    public void testCacheEhcacheTimeToLiveSeconds() {
        FrameworkProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
        int val = FrameworkDefaults.Cache.Ehcache.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheEhcacheMaxEntries() {
        FrameworkProperties.Cache.Ehcache obj = properties.getCache().getEhcache();
        long val = FrameworkDefaults.Cache.Ehcache.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanConfigFile() {
        FrameworkProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
        String val = FrameworkDefaults.Cache.Infinispan.configFile;
        assertThat(obj.getConfigFile()).isEqualTo(val);
        val = "1" + val;
        obj.setConfigFile(val);
        assertThat(obj.getConfigFile()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanStatsEnabled() {
        FrameworkProperties.Cache.Infinispan obj = properties.getCache().getInfinispan();
        boolean val = FrameworkDefaults.Cache.Infinispan.statsEnabled;
        assertThat(obj.isStatsEnabled()).isEqualTo(val);
        val = !val;
        obj.setStatsEnabled(val);
        assertThat(obj.isStatsEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanLocalTimeToLiveSeconds() {
        FrameworkProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
        long val = FrameworkDefaults.Cache.Infinispan.Local.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanLocalMaxEntries() {
        FrameworkProperties.Cache.Infinispan.Local obj = properties.getCache().getInfinispan().getLocal();
        long val = FrameworkDefaults.Cache.Infinispan.Local.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanDistributedTimeToLiveSeconds() {
        FrameworkProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
        long val = FrameworkDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanDistributedMaxEntries() {
        FrameworkProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
        long val = FrameworkDefaults.Cache.Infinispan.Distributed.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanDistributedInstanceCount() {
        FrameworkProperties.Cache.Infinispan.Distributed obj = properties.getCache().getInfinispan().getDistributed();
        int val = FrameworkDefaults.Cache.Infinispan.Distributed.instanceCount;
        assertThat(obj.getInstanceCount()).isEqualTo(val);
        val++;
        obj.setInstanceCount(val);
        assertThat(obj.getInstanceCount()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanReplicatedTimeToLiveSeconds() {
        FrameworkProperties.Cache.Infinispan.Replicated obj = properties.getCache().getInfinispan().getReplicated();
        long val = FrameworkDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
        val++;
        obj.setTimeToLiveSeconds(val);
        assertThat(obj.getTimeToLiveSeconds()).isEqualTo(val);
    }

    @Test
    public void testCacheInfinispanReplicatedMaxEntries() {
        FrameworkProperties.Cache.Infinispan.Replicated obj = properties.getCache().getInfinispan().getReplicated();
        long val = FrameworkDefaults.Cache.Infinispan.Replicated.maxEntries;
        assertThat(obj.getMaxEntries()).isEqualTo(val);
        val++;
        obj.setMaxEntries(val);
        assertThat(obj.getMaxEntries()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedEnabled() {
        FrameworkProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        boolean val = FrameworkDefaults.Cache.Memcached.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = true;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedServers() {
        FrameworkProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        String val = FrameworkDefaults.Cache.Memcached.servers;
        assertThat(obj.getServers()).isEqualTo(val);
        val = "myserver:1337";
        obj.setServers(val);
        assertThat(obj.getServers()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedExpiration() {
        FrameworkProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        int val = FrameworkDefaults.Cache.Memcached.expiration;
        assertThat(obj.getExpiration()).isEqualTo(val);
        val++;
        obj.setExpiration(val);
        assertThat(obj.getExpiration()).isEqualTo(val);
    }

    @Test
    public void testCacheMemcachedUseBinaryProtocol() {
        FrameworkProperties.Cache.Memcached obj = properties.getCache().getMemcached();
        boolean val = FrameworkDefaults.Cache.Memcached.useBinaryProtocol;
        assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
        val = false;
        obj.setUseBinaryProtocol(val);
        assertThat(obj.isUseBinaryProtocol()).isEqualTo(val);
    }

    @Test
    public void testMailFrom() {
        FrameworkProperties.Mail obj = properties.getMail();
        String val = FrameworkDefaults.Mail.from;
        assertThat(obj.getFrom()).isEqualTo(val);
        val = "1" + val;
        obj.setFrom(val);
        assertThat(obj.getFrom()).isEqualTo(val);
    }

    @Test
    public void testMailBaseUrl() {
        FrameworkProperties.Mail obj = properties.getMail();
        String val = FrameworkDefaults.Mail.baseUrl;
        assertThat(obj.getBaseUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setBaseUrl(val);
        assertThat(obj.getBaseUrl()).isEqualTo(val);
    }

    @Test
    public void testMailEnabled() {
        FrameworkProperties.Mail obj = properties.getMail();
        boolean val = FrameworkDefaults.Mail.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationAccessTokenUri() {
        FrameworkProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = FrameworkDefaults.Security.ClientAuthorization.accessTokenUri;
        assertThat(obj.getAccessTokenUri()).isEqualTo(val);
        val = "1" + val;
        obj.setAccessTokenUri(val);
        assertThat(obj.getAccessTokenUri()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationTokenServiceId() {
        FrameworkProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = FrameworkDefaults.Security.ClientAuthorization.tokenServiceId;
        assertThat(obj.getTokenServiceId()).isEqualTo(val);
        val = "1" + val;
        obj.setTokenServiceId(val);
        assertThat(obj.getTokenServiceId()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationClientId() {
        FrameworkProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = FrameworkDefaults.Security.ClientAuthorization.clientId;
        assertThat(obj.getClientId()).isEqualTo(val);
        val = "1" + val;
        obj.setClientId(val);
        assertThat(obj.getClientId()).isEqualTo(val);
    }

    @Test
    public void testSecurityClientAuthorizationClientSecret() {
        FrameworkProperties.Security.ClientAuthorization obj = properties.getSecurity().getClientAuthorization();
        String val = FrameworkDefaults.Security.ClientAuthorization.clientSecret;
        assertThat(obj.getClientSecret()).isEqualTo(val);
        val = "1" + val;
        obj.setClientSecret(val);
        assertThat(obj.getClientSecret()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtSecret() {
        FrameworkProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        String val = FrameworkDefaults.Security.Authentication.Jwt.secret;
        assertThat(obj.getSecret()).isEqualTo(val);
        val = "1" + val;
        obj.setSecret(val);
        assertThat(obj.getSecret()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtBase64Secret() {
        FrameworkProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        String val = FrameworkDefaults.Security.Authentication.Jwt.base64Secret;
        assertThat(obj.getSecret()).isEqualTo(val);
        val = "1" + val;
        obj.setBase64Secret(val);
        assertThat(obj.getBase64Secret()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtTokenValidityInSeconds() {
        FrameworkProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        long val = FrameworkDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;
        assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
        val++;
        obj.setTokenValidityInSeconds(val);
        assertThat(obj.getTokenValidityInSeconds()).isEqualTo(val);
    }

    @Test
    public void testSecurityAuthenticationJwtTokenValidityInSecondsForRememberMe() {
        FrameworkProperties.Security.Authentication.Jwt obj = properties.getSecurity().getAuthentication().getJwt();
        long val = FrameworkDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;
        assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
        val++;
        obj.setTokenValidityInSecondsForRememberMe(val);
        assertThat(obj.getTokenValidityInSecondsForRememberMe()).isEqualTo(val);
    }

    @Test
    public void testSecurityRememberMeKey() {
        FrameworkProperties.Security.RememberMe obj = properties.getSecurity().getRememberMe();
        String val = FrameworkDefaults.Security.RememberMe.key;
        assertThat(obj.getKey()).isEqualTo(val);
        val = "1" + val;
        obj.setKey(val);
        assertThat(obj.getKey()).isEqualTo(val);
    }

    @Test
    public void testSwaggerTitle() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.title;
        assertThat(obj.getTitle()).isEqualTo(val);
        val = "1" + val;
        obj.setTitle(val);
        assertThat(obj.getTitle()).isEqualTo(val);
    }

    @Test
    public void testSwaggerDescription() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.description;
        assertThat(obj.getDescription()).isEqualTo(val);
        val = "1" + val;
        obj.setDescription(val);
        assertThat(obj.getDescription()).isEqualTo(val);
    }

    @Test
    public void testSwaggerVersion() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.version;
        assertThat(obj.getVersion()).isEqualTo(val);
        val = "1" + val;
        obj.setVersion(val);
        assertThat(obj.getVersion()).isEqualTo(val);
    }

    @Test
    public void testSwaggerTermsOfServiceUrl() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.termsOfServiceUrl;
        assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setTermsOfServiceUrl(val);
        assertThat(obj.getTermsOfServiceUrl()).isEqualTo(val);
    }

    @Test
    public void testSwaggerContactName() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.contactName;
        assertThat(obj.getContactName()).isEqualTo(val);
        val = "1" + val;
        obj.setContactName(val);
        assertThat(obj.getContactName()).isEqualTo(val);
    }

    @Test
    public void testSwaggerContactUrl() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.contactUrl;
        assertThat(obj.getContactUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setContactUrl(val);
        assertThat(obj.getContactUrl()).isEqualTo(val);
    }

    @Test
    public void testSwaggerContactEmail() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.contactEmail;
        assertThat(obj.getContactEmail()).isEqualTo(val);
        val = "1" + val;
        obj.setContactEmail(val);
        assertThat(obj.getContactEmail()).isEqualTo(val);
    }

    @Test
    public void testSwaggerLicense() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.license;
        assertThat(obj.getLicense()).isEqualTo(val);
        val = "1" + val;
        obj.setLicense(val);
        assertThat(obj.getLicense()).isEqualTo(val);
    }

    @Test
    public void testSwaggerLicenseUrl() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.licenseUrl;
        assertThat(obj.getLicenseUrl()).isEqualTo(val);
        val = "1" + val;
        obj.setLicenseUrl(val);
        assertThat(obj.getLicenseUrl()).isEqualTo(val);
    }

    @Test
    public void testSwaggerDefaultIncludePattern() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.defaultIncludePattern;
        assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
        val = "1" + val;
        obj.setDefaultIncludePattern(val);
        assertThat(obj.getDefaultIncludePattern()).isEqualTo(val);
    }

    @Test
    public void testSwaggerHost() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String val = FrameworkDefaults.Swagger.host;
        assertThat(obj.getHost()).isEqualTo(val);
        val = "1" + val;
        obj.setHost(val);
        assertThat(obj.getHost()).isEqualTo(val);
    }

    @Test
    public void testSwaggerProtocols() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        String[] def = FrameworkDefaults.Swagger.protocols;
        ArrayList<String> val;
        if (def != null) {
            val = newArrayList(def);
            assertThat(obj.getProtocols()).containsExactlyElementsOf(newArrayList(val));
        } else {
            assertThat(obj.getProtocols()).isNull();
            def = new String[1];
            val = new ArrayList<>(1);
        }
        val.add("1");
        obj.setProtocols(val.toArray(def));
        assertThat(obj.getProtocols()).containsExactlyElementsOf(newArrayList(val));
    }

    @Test
    public void testSwaggerUseDefaultResponseMessages() {
        FrameworkProperties.Swagger obj = properties.getSwagger();
        boolean val = FrameworkDefaults.Swagger.useDefaultResponseMessages;
        assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
        val = false;
        obj.setUseDefaultResponseMessages(val);
        assertThat(obj.isUseDefaultResponseMessages()).isEqualTo(val);
    }

    @Test
    public void testMetricsJmxEnabled() {
        FrameworkProperties.Metrics.Jmx obj = properties.getMetrics().getJmx();
        boolean val = FrameworkDefaults.Metrics.Jmx.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testMetricsLogsEnabled() {
        FrameworkProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
        boolean val = FrameworkDefaults.Metrics.Logs.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testMetricsLogsReportFrequency() {
        FrameworkProperties.Metrics.Logs obj = properties.getMetrics().getLogs();
        long val = FrameworkDefaults.Metrics.Logs.reportFrequency;
        assertThat(obj.getReportFrequency()).isEqualTo(val);
        val++;
        obj.setReportFrequency(val);
        assertThat(obj.getReportFrequency()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashEnabled() {
        FrameworkProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        boolean val = FrameworkDefaults.Logging.Logstash.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashHost() {
        FrameworkProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        String val = FrameworkDefaults.Logging.Logstash.host;
        assertThat(obj.getHost()).isEqualTo(val);
        val = "1" + val;
        obj.setHost(val);
        assertThat(obj.getHost()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashPort() {
        FrameworkProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        int val = FrameworkDefaults.Logging.Logstash.port;
        assertThat(obj.getPort()).isEqualTo(val);
        val++;
        obj.setPort(val);
        assertThat(obj.getPort()).isEqualTo(val);
    }

    @Test
    public void testLoggingLogstashQueueSize() {
        FrameworkProperties.Logging.Logstash obj = properties.getLogging().getLogstash();
        int val = FrameworkDefaults.Logging.Logstash.queueSize;
        assertThat(obj.getQueueSize()).isEqualTo(val);
        val++;
        obj.setQueueSize(val);
        assertThat(obj.getQueueSize()).isEqualTo(val);
    }

    @Test
    public void testSocialRedirectAfterSignIn() {
        FrameworkProperties.Social obj = properties.getSocial();
        String val = FrameworkDefaults.Social.redirectAfterSignIn;
        assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
        val = "1" + val;
        obj.setRedirectAfterSignIn(val);
        assertThat(obj.getRedirectAfterSignIn()).isEqualTo(val);
    }

    @Test
    public void testGatewayAuthorizedMicroservicesEndpoints() {
        FrameworkProperties.Gateway obj = properties.getGateway();
        Map<String, List<String>> val = FrameworkDefaults.Gateway.authorizedMicroservicesEndpoints;
        assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
        val.put("1", null);
        obj.setAuthorizedMicroservicesEndpoints(val);
        assertThat(obj.getAuthorizedMicroservicesEndpoints()).isEqualTo(val);
    }

    @Test
    public void testGatewayRateLimitingEnabled() {
        FrameworkProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
        boolean val = FrameworkDefaults.Gateway.RateLimiting.enabled;
        assertThat(obj.isEnabled()).isEqualTo(val);
        val = !val;
        obj.setEnabled(val);
        assertThat(obj.isEnabled()).isEqualTo(val);
    }

    @Test
    public void testGatewayRateLimitingLimit() {
        FrameworkProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
        long val = FrameworkDefaults.Gateway.RateLimiting.limit;
        assertThat(obj.getLimit()).isEqualTo(val);
        val++;
        obj.setLimit(val);
        assertThat(obj.getLimit()).isEqualTo(val);
    }

    @Test
    public void testGatewayRateLimitingDurationInSeconds() {
        FrameworkProperties.Gateway.RateLimiting obj = properties.getGateway().getRateLimiting();
        int val = FrameworkDefaults.Gateway.RateLimiting.durationInSeconds;
        assertThat(obj.getDurationInSeconds()).isEqualTo(val);
        val++;
        obj.setDurationInSeconds(val);
        assertThat(obj.getDurationInSeconds()).isEqualTo(val);
    }

    @Test
    public void testRegistryPassword() {
        FrameworkProperties.Registry obj = properties.getRegistry();
        String val = FrameworkDefaults.Registry.password;
        assertThat(obj.getPassword()).isEqualTo(val);
        val = "1" + val;
        obj.setPassword(val);
        assertThat(obj.getPassword()).isEqualTo(val);
    }

    @Test
    public void testHttpUseUndertowUserCipherSuitesOrder(){
        FrameworkProperties.Http obj = properties.getHttp();
        boolean val = FrameworkDefaults.Http.useUndertowUserCipherSuitesOrder;
        assertThat(obj.isUseUndertowUserCipherSuitesOrder()).isEqualTo(val);
        val = !val;
        obj.setUseUndertowUserCipherSuitesOrder(val);
        assertThat(obj.isUseUndertowUserCipherSuitesOrder()).isEqualTo(val);
    }

}
