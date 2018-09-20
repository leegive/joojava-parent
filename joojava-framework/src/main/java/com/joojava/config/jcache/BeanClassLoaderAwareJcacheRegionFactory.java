package com.joojava.config.jcache;

import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author leegive
 *
 * 修正了Spring Bug 2.0.3中引入的Spring类加载器问题。
 * 允许使用相同的类加载器用于EHCache，既用于Spring Cache抽象又用于Hibernate  2nd level cache
 */
public class BeanClassLoaderAwareJcacheRegionFactory extends NoDefaultJcacheRegionFactory {

    private static volatile ClassLoader classLoader;

    @Override
    protected CacheManager getCacheManager(Properties properties) {
        Objects.requireNonNull(classLoader, "Please set Spring's classloader in the setBeanClassLoader method before using this class in Hibernate");
        CachingProvider cachingProvider = getCachingProvider(properties);
        String cacheManagerUri = getProp(properties, CONFIG_URI);

        URI uri = getUri(cachingProvider, cacheManagerUri);
        CacheManager cacheManager = cachingProvider.getCacheManager(uri, classLoader);

        setBeanClassLoader(null);
        return cacheManager;
    }

    private URI getUri(CachingProvider cachingProvider, String cacheManagerUri) {
        URI uri;
        if (cacheManagerUri != null) {
            try {
                uri = new URI(cacheManagerUri);
            }
            catch (URISyntaxException e) {
                throw new CacheException("Couldn't create URI from " + cacheManagerUri, e);
            }
        }
        else {
            uri = cachingProvider.getDefaultURI();
        }
        return uri;
    }

    public static void setBeanClassLoader(ClassLoader classLoader) {
        BeanClassLoaderAwareJcacheRegionFactory.classLoader = classLoader;
    }

}
