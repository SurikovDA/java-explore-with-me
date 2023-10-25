package ru.practicum.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {
    @NotBlank(message = "Не может быть пустым")
    @Size(min = 10, max = 2000, message = "Min размер = 10 Max размер = 20000")
    private String text;
}
