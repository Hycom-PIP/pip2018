package pl.hycom.ip2018.searchengine.aggregate.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link AggregateSearch} to get appropriate data type from i.e String query
 */
@Slf4j
public class DefaultAggregateSearch implements AggregateSearch {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Environment environment;

    private ArrayList<String> providers;

    @PostConstruct
    public void initializeProviders() {
        providers = new ArrayList<>(Arrays.asList(environment.getProperty("clients").split(",")));
    }

    private Set<AggregateSearch> getClients() {

        Set<AggregateSearch> clients = new HashSet<>();

        for (String provider : providers) {
            List<ServiceInstance> services = discoveryClient.getInstances(provider);

            if (services.isEmpty()) {
                continue;
            }

            String name = services.get(0).getUri().toString();
            AggregateSearch feignClient = Feign.builder().target(AggregateSearch.class, name);


            if (feignClient != null) {
                clients.add(feignClient);
            }
        }

        return clients;
    }

    /**
     * By submitting a query, we receive a ready answer formed as {@link ProviderResponse} data model based on all registered clients
     *
     * @param query
     *            we are searching for
     * @return ProviderResponse object
     */
    @Override
    public ProviderResponse getResponse(String query) {

        if (log.isInfoEnabled()) {
            log.info("Requesting aggregate search for {}", query);
        }

        List<ProviderResponse> output = new ArrayList<>();

        for (AggregateSearch client : getClients()) {
            output.add(client.getResponse(query));
        }

        return join(output);
    }

    private ProviderResponse join(List<ProviderResponse> partialList) {
        ProviderResponse result = new ProviderResponse();
        result.setResults(new ArrayList<>());
        partialList.forEach(partial -> result.getResults().addAll(partial.getResults()));
        return result;
    }
}

//package pl.hycom.ip2018.searchengine.aggregate.service;
//
//import feign.FeignException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import pl.hycom.ip2018.searchengine.aggregate.intercomm.GoogleClient;
//import pl.hycom.ip2018.searchengine.aggregate.intercomm.GoogleDriveClient;
//import pl.hycom.ip2018.searchengine.aggregate.intercomm.LocalClient;
//import pl.hycom.ip2018.searchengine.aggregate.intercomm.WikiClient;
//import pl.hycom.ip2018.searchengine.aggregate.notation.Usage;
//import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;
//import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Implementation of {@link AggregateSearch} to get appropriate data type from i.e String query
// */
//@Slf4j
//public class DefaultAggregateSearch implements AggregateSearch {
//
//    @Autowired
//    Environment environment;
//
//    @Usage
//    WikiClient wikiClient;
//
//    @Usage
//    GoogleClient googleClient;
//
//    @Usage
//    LocalClient localClient;
//
//    @Usage
//    GoogleDriveClient googleDriveClient;
//
//
//    /**
//     * By submitting a query, we receive a ready answer formed as {@link ProviderResponse} data model
//     * based on all registered clients
//     * @param query we are searching for
//     * @return ProviderResponse object
//     */
//    @Override
//    public ProviderResponse getResponse(String query) {
//
//        if (log.isInfoEnabled()) {
//            log.info("Requesting aggregate search for {}", query);
//        }
//
//        String message = "";
//        List<ProviderResponse> output = new ArrayList<>();
//        Class<?> obj = DefaultAggregateSearch.class;
//            try {
//                for(Field field : obj.getDeclaredFields()) {
//                    if(field.isAnnotationPresent(Usage.class))  {
//                        message = field.getName();
//                        if (environment.getProperty("client." + message + ".enabled").equals("true")) {
//                            field.setAccessible(true);
//
//                            try {
//                                  ProviderSearch value = (ProviderSearch)field.get(this);
//                                  ProviderResponse response= value.getResponse(query);
//                                  output.add(response);
//                            } catch (FeignException e) {
//                                if (log.isErrorEnabled()) {
//                                    log.error("Client {} hasn't been registered", message, e);
//                                }
//                            }
//
//                        }
//                    }
//                }
//
//            } catch (IllegalAccessException e) {
//                if (log.isErrorEnabled()) {
//                    log.error("Error has been occurred in client: {}", message);
//                }
//            }
//
//        return join(output);
//    }
//
//    private ProviderResponse join(List<ProviderResponse> partialList) {
//        ProviderResponse result = new ProviderResponse();
//        result.setResults(new ArrayList<>());
//        partialList.forEach(partial ->
//                result.getResults().addAll(partial.getResults())
//        );
//        return result;
//    }
//}
