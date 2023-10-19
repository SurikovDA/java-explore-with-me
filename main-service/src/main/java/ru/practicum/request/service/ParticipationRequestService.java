package ru.practicum.request.service;

import ru.practicum.request.dto.EventParticipationRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventParticipationRequestStatusUpdateResponseDto;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;
import java.time.LocalDateTime;

public interface ParticipationRequestService {
    ParticipationRequestDto createParticipationRequest(Long userId, Long eventId, LocalDateTime localDateTime);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long eventId);

    List<ParticipationRequestDto> getParticipationRequest(Long userId);

    List<ParticipationRequestDto> getAllUserEventRequests(Long eventId, Long userId);

    EventParticipationRequestStatusUpdateResponseDto updateParticipationRequestsStatus(
            EventParticipationRequestStatusUpdateRequestDto updater,
            Long eventId,
            Long userId
    );
}
