package ru.yandex.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    private String name;
    private Long id;
    private String email;
    private String login;
    private LocalDate birthday;
    private Set<Long> friends;
    private Set<Long> likes;

}