package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();


    public User getUserById(Integer id);


    public User createUser(User user);


    public User updateUser(User user);



    public void addFriend(Integer id, Integer friendId);


    public void removeFriend(Integer id, Integer userId);


    public List<User> getFriends(Integer id);


    public List<User> getCrossFriends(Integer id, Integer userId);


    public void reset();

    public void createUserId(User user);

}
