package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.practicum.comments.dto.CommentResponseDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.RequestStatus;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.EntityNotFoundException;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.repository.ParticipationRequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ParticipationRequestRepository participationRequest;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentResponseDto addComment(Long userId, Long eventId, NewCommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Такого пользователя не существует!"));
        Event event = getEventById(eventId);

        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("Такого события не существует!");
        }

        if (!Objects.equals(user.getId(), event.getInitiator().getId())) {
            List<ParticipationRequest> requests = participationRequest.findAllByEventIdAndStatusAndRequesterId(eventId, RequestStatus.CONFIRMED, userId);
            if (requests.isEmpty()) {
                throw new ConflictException("Нет доступа!");
            }
        }
        Optional<Comment> foundComment = commentRepository.findByEventIdAndAuthorId(eventId, userId);
        if (foundComment.isPresent()) {
            throw new ConflictException("Разрешен 1 комментарий");
        }
        log.info("Комментарий добавлен");
        return CommentMapper.toCommentResponseDto(commentRepository.save(CommentMapper
                .toComment(commentDto, user, event)));
    }

    @Override
    @Transactional
    public void deleteCommentById(Long commentId, Long userId) {
        Comment comment = getCommentById(commentId);
        checkIfUserIsTheAuthor(comment.getAuthor().getId(), userId);
        log.info("Комментарий передан на удаление");
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, Long userId, NewCommentDto commentDto) {
        Comment comment = getCommentById(commentId);
        checkIfUserIsTheAuthor(comment.getAuthor().getId(), userId);
        String newText = commentDto.getText();
        if (StringUtils.hasLength(newText)) {
            comment.setText(newText);
        }
        log.info("Комментарий обновлен");
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public List<CommentResponseDto> getAllCommentsByEventId(Long eventId, Integer from, Integer size) {
        getEventById(eventId);
        PageRequest pageRequest = PageRequest.of(from, size);
        log.info("Все комментарии получены");
        return CommentMapper.toListOfCommentResponseDto(commentRepository.findAllByEventIdOrderByCreatedOnDesc(eventId, pageRequest));
    }

    @Override
    @Transactional
    public List<CommentResponseDto> get10LatestCommentsByEventId(Long eventId) {
        getEventById(eventId);
        log.info("10 комментариев получено");
        return CommentMapper.toListOfCommentResponseDto(commentRepository.findTop10ByEventIdOrderByCreatedOnDesc(eventId));
    }

    private void checkIfUserIsTheAuthor(Long authorId, Long userId) {
        if (!Objects.equals(authorId, userId)) {
            throw new ConflictException("Такого автора не существует!");
        }
    }

    private Comment getCommentById(Long commentId) {
        log.info("Комментарий с id={} передан на получение", commentId);
        return commentRepository.findById(commentId).orElseThrow(() -> new UserNotFoundException("Комментарий отсутствует!"));
    }

    private Event getEventById(Long eventId) {
        log.info("Событие с id={} передано на получение", eventId);
        return eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Событие отсутствует!"));
    }
}
