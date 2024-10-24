package ru.yandex.model;

import lombok.Data;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private String name;
    private Long id;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
    private Long like;
}
