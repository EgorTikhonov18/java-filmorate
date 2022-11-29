package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {
	FilmController filmController;
	UserController userController;
	User user;
	Film film;

	@Test
	void checkUserValidation() {
		userController = new UserController();
		user =  User.builder()
				.email("email@mail.ru")
				.login("login")
				.name("name")
				.birthday(LocalDate.of(2022,11,29))
				.build();
		userController.create(user);
		assertEquals(29, filmController.findAll().size());
	}
	@Test
	void checkFilmValidation() throws ValidationException {
		filmController = new FilmController();
		film =  Film.builder()
				.name("Film 1")
				.description("Film 1 description")
				.releaseDate(LocalDate.of(2022,11,29))
				.duration(65)
				.build();
		filmController.create(film);
		assertEquals(29, filmController.findAll().size());

	}
	@Test
	void checkFilmReleaseDate() {
		filmController = new FilmController();
		film =  Film.builder()
				.name("name")
				.description("Film 1 description")
				.releaseDate(LocalDate.of(1997,7,10))
				.duration(65)
				.build();
		ValidationException exc = assertThrows(
				ValidationException.class, () -> filmController.create(film)
		);
		Assertions.assertEquals("Некорректная дата релиза", exc.getMessage());
	}
}
