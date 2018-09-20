package com.joojava.config.jcache;

import org.hibernate.cache.jcache.JCacheRegionFactory;
import org.hibernate.cache.spi.CacheDataDescription;

import javax.cache.Cache;
import java.util.Properties;

/**
 * @author leegive
 *
 */
public class NoDefaultJcacheRegionFactory extends JCacheRegionFactory {

    public static final String EXCEPTION_MESSAGE = "All Hibernate caches should be created upfront. Please update CacheConfiguration.java to add";

    @Override
    protected Cache<Object, Object> createCache(String regionName, Properties properties, CacheDataDescription metadata) {
        throw new IllegalStateException(EXCEPTION_MESSAGE + " " + regionName);
    }
}
