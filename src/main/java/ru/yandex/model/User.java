package ru.yandex.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private String name;
    private Long id;
    private String email;
    private String login;
    private LocalDate birthday;
    private Set<Long> friends;
    private int status;
}