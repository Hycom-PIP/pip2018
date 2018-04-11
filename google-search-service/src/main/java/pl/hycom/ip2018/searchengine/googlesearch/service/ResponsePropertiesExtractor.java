package pl.hycom.ip2018.searchengine.googlesearch.service;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            String title = (String) tempMap.get(titleKey);
            String link = (String) tempMap.get(linkKey);
            String snippet = (String) tempMap.get(snippetKey);
            Map pageMap = (Map) tempMap.get(pageMapKey);
            String articleModifiedTime = null;
            if (pageMap != null) {
                List<Map> metaTags = (List<Map>) pageMap.get(metaTagsKey);
                articleModifiedTime = null;

                if (metaTags != null) {
                    List tempResult = metaTags
                            .stream()
                            .filter(o -> o.get(articleModifiedTimeKey) != null)
                            .collect(Collectors.toList());
                    if (!tempResult.isEmpty()) {
                        articleModifiedTime = (String) tempResult.get(0);
                    }
                }
            }

            singleItem.put(titleKey, title);
            singleItem.put(linkKey, link);
            singleItem.put(snippetKey, snippet);
            singleItem.put(articleModifiedTimeKey, articleModifiedTime);
            items.add(singleItem);
        }
        result.put(itemsKey, items);
        return result;
    }
}
