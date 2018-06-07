package pl.hycom.ip2018.searchengine.aggregate.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String QUERY_CACHE_NAME = "query_cache";

    @Bean
    public LoadingCache<String, List<String>> queryCache() {
        CacheLoader<String, List<String>> loader;
        loader = new CacheLoader<String, List<String>>() {
            @Override
            public List<String> load(String key) {
                return new ArrayList<>();
            }
        };

        LoadingCache<String, List<String>> cache;
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(loader);
        return cache;
    }

    private List<String> createExpensiveGrapgh(String key) {
        return null;
    }
}
