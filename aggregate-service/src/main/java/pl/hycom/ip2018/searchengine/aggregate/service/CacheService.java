package pl.hycom.ip2018.searchengine.aggregate.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheService {

    @Autowired
    private LoadingCache<String, List<String>> queryCache;

    @Async
    public void addQueryToCache(String userId, String query) {
        if (StringUtils.isBlank(userId)) {
            return;
        }

        try {
            List<String> userQueries = queryCache.get(userId);
            userQueries.add(query);
            queryCache.put(userId, userQueries);

        } catch (ExecutionException e) {
            if (log.isErrorEnabled()) {
                log.error("Error during adding query to cache", e);
            }
        }
    }

    public List<String> getUserHistoryValues(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Collections.emptyList();
        }

        try {
            return queryCache.get(userId);
        } catch (ExecutionException e) {
            if (log.isErrorEnabled()) {
                log.error("Error during reading cache", e);
            }
        }

        return Collections.emptyList();
    }
}
