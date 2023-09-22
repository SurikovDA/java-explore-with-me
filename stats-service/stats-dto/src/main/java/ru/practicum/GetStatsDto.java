package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStatsDto {
    @NotNull
    private String start;
    @NotNull
    private String end;
    private List<String> uris;
    private Boolean unique;
}
