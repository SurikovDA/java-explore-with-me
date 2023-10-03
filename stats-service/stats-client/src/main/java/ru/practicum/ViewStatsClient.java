package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ViewStatsClient extends BaseClient {
    private static final String API_PREFIX = "/stats";

    @Autowired
    public ViewStatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        Map<String, Object> parameters;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeStart = start.format(formatter);
        String dateTimeEnd = end.format(formatter);
        if (uris != null && !uris.isEmpty()) {
            parameters = Map.of(
                    "start", dateTimeStart,
                    "end", dateTimeEnd,
                    "uris", String.join(",", uris),
                    "unique", unique
            );
            return get("?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        } else {
            parameters = Map.of(
                    "start", dateTimeStart,
                    "end", dateTimeEnd,
                    "unique", unique
            );
            return get("?start={start}&end={end}&unique={unique}", parameters);
        }
    }
}
