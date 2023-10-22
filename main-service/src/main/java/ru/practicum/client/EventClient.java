package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventClient {
    private final EventClientService eventClientService;

    public static String formatTimeToString(LocalDateTime time) {
        return EventClientService.formatTimeToString(time);
    }

    public List<EventShortDto> makeEventShortDto(Collection<Event> events) {

        return eventClientService.makeEventShort(events);
    }

    public Map<String, Long> toViewStats(Collection<Event> events) {

        return eventClientService.toViewStats(events);
    }

    public Map<Long, Long> getConfirmedRequests(Collection<Event> events) {
        return eventClientService.getConfirmedRequests(events);
    }
}