package pl.hycom.ip2018.searchengine.aggregate.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String QUERY_CACHE_NAME = "query_cache";

    @Bean
    public LoadingCache<String, String> queryCache() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(loader);
        return cache;
    }
}
