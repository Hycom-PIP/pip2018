package pl.hycom.ip2018.searchengine.googlesearch.service;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for extract useful data from google api response
 */
public class ResponsePropertiesExtractor {

    @Value("${prop.google.itemsKey}")
    private String itemsKey;

    @Value("${prop.google.titleKey}")
    private String titleKey;

    @Value("${prop.google.linkKey}")
    private String linkKey;

    @Value("${prop.google.snippetKey}")
    private String snippetKey;

    @Value("${prop.google.pageMapKey}")
    private String pageMapKey;

    @Value("${prop.google.metaTagsKey}")
    private String metaTagsKey;

    @Value("${articleModifiedTimeKey}")
    private String articleModifiedTimeKey;

    /**
     * Prepare simple Map which contains only properties that we need
     * from huge google response entity
     *
     * @param response mapped json
     * @return Map
     * @throws ClassCastException could have been thrown if Google changed their format of data
     */
    @SuppressWarnings("unchecked")
    public Map<String, List<Map<String, String>>> makeSimpleMapFromResponse
    (Map response) throws ClassCastException {

        Map<String, List<Map<String, String>>> result = new LinkedHashMap<>();
        List<Map<String, String>> items = new ArrayList<>();
        List<Map> tempList = (List<Map>) response.get(itemsKey);

        for (Map tempMap : tempList) {
            Map<String, String> singleItem = new LinkedHashMap<>();
            singleItem.put(titleKey, (String) tempMap.get(titleKey));
            singleItem.put(linkKey, (String) tempMap.get(linkKey));
            singleItem.put(snippetKey, (String) tempMap.get(snippetKey));
            Optional<Map> pageMapOpt = Optional.ofNullable((Map) tempMap.get(pageMapKey));
            pageMapOpt.ifPresent(pageMap -> {
                Optional<List<Map>> metaTagsOpt = Optional.ofNullable((List<Map>) pageMap.get(metaTagsKey));
                metaTagsOpt.ifPresent(metaTags -> {
                    List<Map> tempResult = metaTags
                            .stream()
                            .filter(o -> o.get(articleModifiedTimeKey) != null)
                            .collect(Collectors.toList());
                    if (!tempResult.isEmpty()) {
                        singleItem.put(articleModifiedTimeKey, (String) tempResult
                                .get(0)
                                .get(articleModifiedTimeKey));
                    }
                });
            });
            items.add(singleItem);
        }
        result.put(itemsKey, items);
        return result;
    }
}
