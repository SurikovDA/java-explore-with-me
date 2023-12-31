package ru.practicum.compilation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationControllerPublic {

    private final CompilationService compilationsService;

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return compilationsService.getCompilationById(compId);
    }

    @GetMapping
    public List<CompilationDto> getCompilationsByPinned(@RequestParam(defaultValue = "false") Boolean pinned,
                                                        @RequestParam(value = "from", defaultValue = "0", required = false)
                                                        @PositiveOrZero Integer from,
                                                        @RequestParam(value = "size", defaultValue = "10", required = false)
                                                        @Positive Integer size) {

        return compilationsService.getCompilationsByPinned(pinned, from, size);
    }


}
