package pl.hycom.ip2018.searchengine.aggregate.service;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private LoadingCache<String, List<String>> queryCache;

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

    public List<String> getUserHistoryValues(String userId) {
        try {
            return queryCache.get(userId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
