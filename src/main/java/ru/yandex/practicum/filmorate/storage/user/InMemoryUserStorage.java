package ru.yandex.practicum.filmorate.storage.user;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;
@Component
@Slf4j
@NonNull
@Valid
@NotEmpty
@NotBlank

public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap<>();
    private int id;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(@Valid @NotBlank User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("User with this ID doesn't exist.");
        }
        users.put(user.getId(), user);
        return user;
    }
    @Override
    public User get(int userId) {
        return users.get(userId);
    }
    @Override
    public void addFriend(User user, User friend) {
        if (!users.containsKey(user.getId())) {
            throw new UserNotFoundException("User with this ID doesn't exist.");
        }
        user.getFriends().add(friend.getId());

        friend.getFriends().add(user.getId());

    }
    @Override
    public void deleteFriend(User user, User friend) {
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }
    @Override
    public List<User> getAllFriends(User user) {
        List<User> friends = new ArrayList<>();
        for (int id : user.getFriends()) {
            friends.add(users.get(id));
        }
        return friends;
    }
    @Override
    public List<User> getCommonFriends(User user, User other) {
        List<User> commonFriends = new ArrayList<>();
        for (int id : user.getFriends()) {
            if (other.getFriends().contains(id)) {
                commonFriends.add(users.get(id));
            }
        }
        return commonFriends;
    }
}
