package ru.practicum.comments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    @NotNull(message = "Поле даты создания не может быть пустым!")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdOn;
    @NotNull(message = "Поле событие не может быть пустым!")
    private Long event;
    @NotNull(message = "Поле автор не может быть пустым!")
    private Long author;
    private String text;
}
