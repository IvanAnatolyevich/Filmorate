import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private HashMap<Long, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Получен запрос на создание пользователя {}", user);
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@"))) {
            log.error("Ошибка валидации email");
            throw new ValidationException("Введен имейл неверного формата");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Ошибка валидации login");
            throw new ValidationException("Введен неправильный логин");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.error("Ошибка валидации name");
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Ошибка валидации birthday");
            throw new ValidationException("День рождения не может быть в будушем");
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Создался новый пользователь");
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User newUser) {
        log.info("Получен запрос на обновление пользователя {}", newUser);
        if (newUser.getId() == null) {
            log.error("Ошибка валидации");
            throw new ValidationException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            oldUser.setName(newUser.getName());
            oldUser.setBirthday(newUser.getBirthday());
            log.info("Пользователь обновился");
            return newUser;
        }
        log.error("Ошибка валидации");
        throw new ValidationException("Пост с id = " + newUser.getId() + " не найден");
    }

    @GetMapping
    public Collection<User> allUsers() {
        log.info("Получен запрос на получение пользователей");
        return users.values();
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
