package ru.yandex.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.exception.UserNotFoundException;
import ru.yandex.storage.InMemoryUserStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import ru.yandex.model.User;

@Service
@RequiredArgsConstructor
@Getter
public class UserServiceImpl implements UserService {

    private final InMemoryUserStorage userStorage;
    public User createUser(User user) {
       return userStorage.createUser(user);
    }

    public User deleteUser(User user) {
       return userStorage.deleteUser(user);
    }

    public User updateUser(User newUser) {
       return userStorage.updateUser(newUser);
    }

    public Collection<User> allUsers() {
       return userStorage.allUsers();
    }

    public User addFriend(Long id, Long friendId) {
        if (userStorage.getUsers().containsKey(id)) {
            if (userStorage.getUsers().containsKey(friendId)) {
                User user = userStorage.getUsers().get(id);
                Set<Long> set = userStorage.getUsers().get(id).getFriends();
                set.add(friendId);
                user.setFriends(set);
                userStorage.getUsers().put(id, user);
                return userStorage.getUsers().get(friendId);
            }
            throw new UserNotFoundException("Пост с id = " + friendId + " не найден");
        }
        throw new UserNotFoundException("Пост с id = " + id + " не найден");
    }

    public User deleteFriend(Long id, Long friendId) {
        if (userStorage.getUsers().containsKey(id)) {
            if (userStorage.getUsers().containsKey(friendId)) {
                if (userStorage.getUsers().get(id).getFriends().contains(friendId)) {
                    User user = userStorage.getUsers().get(id);
                    Set<Long> set = userStorage.getUsers().get(id).getFriends();
                    set.remove(friendId);
                    user.setFriends(set);
                    userStorage.getUsers().put(id, user);
                    return userStorage.getUsers().get(friendId);
                }
                throw new UserNotFoundException("Пост с id = " + friendId + " не найден");
            }
            throw new UserNotFoundException("Пост с id = " + friendId + " не найден");
        }
        throw new UserNotFoundException("Пост с id = " + friendId + " не найден");
    }

    public Collection<Long> allFriends(Long id) {
        return userStorage.getUsers().get(id).getFriends();
    }

    public Collection<Long> generalFriends(Long id, Long otherId) {
        Set<Long> friends = userStorage.getUsers().get(id).getFriends().stream()
                .filter(x -> (userStorage.getUsers().get(otherId).getFriends()
                        .stream()
                        .anyMatch( y -> (y == x))
                ))
                .collect(Collectors.toSet());
        return friends;
    }

    public User getUserId(Long id) {
        return userStorage.getUsers().get(id);
    }
}
