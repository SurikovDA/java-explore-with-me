package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users/{userId}/comments")
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(@PathVariable Long userId,
                                         @RequestParam Long eventId,
                                         @Valid @RequestBody NewCommentDto commentDto) {
        log.info("Получен запрос POST /users/{}/comments?eventId={}", userId, eventId);
        return commentService.addComment(userId, eventId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long userId,
                                  @PathVariable Long commentId) {
        log.info("Получен запрос DELETE /users/{}/comments/{}", userId, commentId);
        commentService.deleteCommentById(commentId, userId);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment(@PathVariable Long userId,
                                            @PathVariable Long commentId,
                                            @Valid @RequestBody NewCommentDto commentDto) {
        log.info("Получен запрос PATCH /users/{}/comments/{}", userId, commentId);
        return commentService.updateComment(commentId, userId, commentDto);
    }
}