package ru.practicum.service;

import ru.practicum.EndpointHitDto;
import ru.practicum.GetStatsDto;
import ru.practicum.ViewStatsDto;

import java.util.List;

public interface StatsService {

    EndpointHitDto saveHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViewStats(GetStatsDto getStatsDto);
}
