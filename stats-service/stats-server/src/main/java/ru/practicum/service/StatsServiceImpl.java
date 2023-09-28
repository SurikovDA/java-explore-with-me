package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointHitDto;
import ru.practicum.GetStatsDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ru.practicum.mapper.EndpointHitMapper.*;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class StatsServiceImpl implements StatsService {

    private final EndpointHitRepository endpointHitRepository;

    @Transactional
    @Override
    public EndpointHitDto saveHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = toEndpointHit(endpointHitDto);
        log.info("Save endpoint hit.");
        return toEndpointHitDto(endpointHitRepository.save(endpointHit));
    }

    @Override
    public List<ViewStatsDto> getViewStats(GetStatsDto getStatsDto) {
        if (getStatsDto == null) {
            log.error("getStatsDto is null in getViewStats method.");
            return Collections.emptyList();
        }

        LocalDateTime startDate = LocalDateTime.parse(getStatsDto.getStart(), DATE_TIME_FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(getStatsDto.getEnd(), DATE_TIME_FORMATTER);

        List<ViewStatsDto> viewStats;
        List<String> uris = getStatsDto.getUris();

        if (uris == null || uris.isEmpty()) {
            viewStats = (getStatsDto.getUnique()
                    ? endpointHitRepository.getStatsUniqueByTime(startDate, endDate)
                    : endpointHitRepository.getAllStatsByTime(startDate, endDate));
        } else {
            viewStats = (getStatsDto.getUnique()
                    ? endpointHitRepository.getStatsUniqueByTimeAndUris(startDate, endDate, uris)
                    : endpointHitRepository.getStatsByTimeAndUris(startDate, endDate, uris));
        }
        return viewStats;
    }

}
