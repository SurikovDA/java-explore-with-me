package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/events/{eventId}/comments")
public class EventCommentController {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getAllCommentsByEventId(
            @PathVariable Long eventId,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        log.info("Получен запрос GET /events/{}/comments", eventId);
        return commentService.getAllCommentsByEventId(eventId, from, size);
    }

    @GetMapping("/last10")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> get10LatestCommentsByEventId(@PathVariable Long eventId) {
        log.info("Получен запрос GET /events/{}/comments/last10", eventId);
        return commentService.get10LatestCommentsByEventId(eventId);
    }
}

