package de.dhbw.skywatch.service;

import de.dhbw.skywatch.dto.nasa.NeoFeedResponse;
import de.dhbw.skywatch.dto.nasa.NeoObject;
import de.dhbw.skywatch.exception.ExternalApiException;
import de.dhbw.skywatch.exception.InvalidDateRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// Talks to the NASA NEO feed API
@Service
public class NasaNeoService {

    private static final Logger log = LoggerFactory.getLogger(NasaNeoService.class);

    // NASA's feed only allows up to 7 days per request
    private static final int MAX_RANGE_DAYS = 7;

    private final RestClient nasaRestClient;
    private final String apiKey;

    public NasaNeoService(RestClient nasaRestClient,
                          @Value("${nasa.api.key}") String apiKey) {
        this.nasaRestClient = nasaRestClient;
        this.apiKey = apiKey;
    }

    // all asteroids NASA lists for one day
    // up to 3 tries if NASA fails: wait 500 ms, then 1 s (GET is idempotent, so retrying is safe)
    @Retryable(
            retryFor = ExternalApiException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500, multiplier = 2.0)
    )
    public List<NeoObject> fetchObjectsForDate(LocalDate date) {
        return fetchFeed(date, date)
                .getOrDefault(date.toString(), Collections.emptyList());
    }

    // all asteroids between start and end (both included), sorted by day
    // same retry rules, a bad range is not retried (different exception)
    @Retryable(
            retryFor = ExternalApiException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500, multiplier = 2.0)
    )
    public List<NeoObject> fetchObjectsForRange(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new InvalidDateRangeException("end must not be before start");
        }
        // +1 because both days count, e.g. 01.-07. = 7 days
        long days = ChronoUnit.DAYS.between(start, end) + 1;
        if (days > MAX_RANGE_DAYS) {
            throw new InvalidDateRangeException("range must be at most " + MAX_RANGE_DAYS + " days, was " + days);
        }

        // NASA groups by date -> flatten into one list
        return fetchFeed(start, end).entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .toList();
    }

    // does the actual HTTP call, returns asteroids grouped by date
    private Map<String, List<NeoObject>> fetchFeed(LocalDate start, LocalDate end) {
        log.info("Calling NASA API for {} to {}", start, end);
        try {
            NeoFeedResponse response = nasaRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/neo/rest/v1/feed")
                            .queryParam("start_date", start.toString())
                            .queryParam("end_date", end.toString())
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    // turn NASA errors into our own exception -> 502 for our clients
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new ExternalApiException("NASA API client error: " + res.getStatusCode());
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new ExternalApiException("NASA API server error: " + res.getStatusCode());
                    })
                    .body(NeoFeedResponse.class);

            if (response == null || response.near_earth_objects() == null) {
                return Collections.emptyMap();
            }
            return response.near_earth_objects();
        } catch (RestClientException ex) {
            // network problems: timeout, unknown host, connection refused ...
            throw new ExternalApiException("Failed to call NASA API: " + ex.getMessage(), ex);
        }
    }
}
