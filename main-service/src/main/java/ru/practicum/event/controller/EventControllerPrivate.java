package ru.practicum.event.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventUpdateRequestDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.EventParticipationRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventParticipationRequestStatusUpdateResponseDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.ParticipationRequestService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class EventControllerPrivate {

    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto createNewEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.createNewEvent(userId, newEventDto);
    }

    @GetMapping
    public List<EventFullDto> getEventByUserId(@PathVariable Long userId,
                                               @RequestParam(value = "from", defaultValue = "0", required = false) Integer from,
                                               @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        if (size <= 0 || from < 0) {
            throw new IllegalReceiveException("Неверно указан параметр");
        }
        return eventService.getEventByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdByInitiator(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId) {
        return eventService.getEventByIdByInitiator(eventId, userId);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getAllUserEventRequests(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId) {
        return participationRequestService.getAllUserEventRequests(eventId, userId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId,
                                          @Valid @RequestBody EventUpdateRequestDto eventDto) {
        return eventService.updateEventByUser(eventDto, eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventParticipationRequestStatusUpdateResponseDto updateParticipationRequestsStatus(
            @Valid @RequestBody EventParticipationRequestStatusUpdateRequestDto updater,
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId
    ) {
        return participationRequestService.updateParticipationRequestsStatus(updater, eventId, userId);
    }
}
