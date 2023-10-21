package ru.practicum.client;

import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EventClientService {
    public List<EventShortDto> makeEventShortDto(Collection<Event> events, Map<String, Long> viewStatsMap,
                                                 Map<Long, Long> confirmedRequests) {
        List<EventShortDto> eventsDto = new ArrayList<>();
        for (Event event : events) {
            Long eventId = event.getId();
            Long reqCount = confirmedRequests.get(eventId);
            Long views = viewStatsMap.get(String.format("/events/%s", eventId));
            if (reqCount == null) {
                reqCount = 0L;
            }
            if (views == null) {
                views = 0L;
            }
            eventsDto.add(
                    EventMapper.toEventDtoShort(event, reqCount, views)
            );
        }
        return eventsDto;
    }
}
