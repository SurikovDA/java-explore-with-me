package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.ParticipationRequestService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class EventPrivateRequestsController {

    private final ParticipationRequestService participationRequestService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ParticipationRequestDto createNewRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return participationRequestService.createParticipationRequest(userId, eventId, localDateTime);
    }

    @GetMapping
    public List<ParticipationRequestDto> getParticipationRequestByUserId(@PathVariable Long userId) {
        return participationRequestService.getParticipationRequest(userId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable Long userId, @PathVariable Long requestId) {

        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }
}
