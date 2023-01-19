package ru.yandex.practicum.filmorate.storage_jdbc.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage_jdbc.UserDao;

import java.util.*;

@Repository("UserDaoImpl")
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void reset() {
        final String sql = "DROP TABLE IF EXISTS users CASCADE; create table IF NOT EXISTS users\n" +
                "(\n" +
                "    user_id  INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,\n" +
                "    email    VARCHAR(30) NOT NULL,\n" +
                "    login    VARCHAR(30) NOT NULL,\n" +
                "    name     VARCHAR(30),\n" +
                "    birthday DATE\n" +
                "    );";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<User> getAllUsers() {
        final String sql = "Select * from users";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        return mappingUser(rs);
    }

    @Override
    public User addUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        user.setId(simpleJdbcInsert.executeAndReturnKey(this.userToMap(user)).intValue());
        return user;
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        final String sql = "SELECT * from USERS where USER_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        List<User> users = mappingUser(rs);
        if (users.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(0));
        }
    }

    @Override
    public User updateUser(User user) {
        final String sql = "update users set email = ?, login = ?, name = ?, " +
                "birthday = ?   where user_id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName()
                , user.getBirthday()
                , user.getId());
        return user;
    }


    private List<User> mappingUser(SqlRowSet rs) {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User(rs.getInt("USER_ID"), rs.getString("NAME"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getDate("BIRTHDAY").toLocalDate());
            users.add(user);
        }
        return users;
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("EMAIL", user.getEmail());
        values.put("LOGIN", user.getLogin());
        values.put("NAME", user.getName());
        values.put("BIRTHDAY", user.getBirthday());

        return values;
    }


}