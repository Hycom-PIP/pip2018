package pl.hycom.ip2018.searchengine.aggregate.service;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.hycom.ip2018.searchengine.aggregate.config.CacheConfig;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private LoadingCache<String, List<String>> queryCache;

//    @Cacheable(CacheConfig.QUERY_CACHE_NAME)
//    public void createCache(String query) {
//        queryCache.getUnchecked(query);
//        System.out.println(queryCache.size());
//    }

    public void addQueryToCache(String userId, String query) {
        try {
            List<String> userQueries = queryCache.get(userId);
            userQueries.add(query);
            queryCache.put(userId, userQueries);
            System.out.println(queryCache.get(userId).toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
