package ru.yandex.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Data
public class Film {
    @NotBlank
    private String name;
    private Long id;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
    private Long like;
    private Set<Long> userLikes;

}
