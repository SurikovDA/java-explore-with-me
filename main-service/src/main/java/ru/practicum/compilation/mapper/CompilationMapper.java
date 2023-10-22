package ru.practicum.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class CompilationMapper {
    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return Compilation
                .builder()
                .title(newCompilationDto.getTitle())
                .events(new HashSet<>())
                .pinned(newCompilationDto.isPinned())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto
                .builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .events(events)
                .pinned(compilation.getPinned())
                .build();
    }

    public static void fromUpdateCompilationDtoToCompilation(UpdateCompilationRequest updateCompilationRequest, Compilation compilation, Set<Event> events) {
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (events != null) {
            compilation.setEvents(events);
        }
        compilation.setPinned(updateCompilationRequest.getPinned());
    }
}