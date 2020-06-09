package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.UserGenerator.*;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitActiveUser() {
        RegistrationDto validValidActiveUser = getValidActiveUser();
        $("[name=login]").setValue(validValidActiveUser.getLogin());
        $("[name=password]").setValue(validValidActiveUser.getPassword());
        $(".button__text").click();
        $("[class='heading heading_size_l heading_theme_alfa-on-white']").waitUntil(exist, 5000);
        $("[class='heading heading_size_l heading_theme_alfa-on-white']").shouldHave(matchesText("Личный кабинет"));
    }

    @Test
    void shouldSubmitBlockedUser() {
        open("http://localhost:9999");
        RegistrationDto validValidAcBlockedUser = getValidBlockedUser();
        $("[name=login]").setValue(validValidAcBlockedUser.getLogin());
        $("[name=password]").setValue(validValidAcBlockedUser.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldSubmitWithIncorrectPassword() {
        open("http://localhost:9999");
        RegistrationDto userWithIncorrectPassword = getUserWithIncorrectPassword();
        $("[name=login]").setValue(userWithIncorrectPassword.getLogin());
        $("[name=password]").setValue(userWithIncorrectPassword.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldSubmitWithIncorrectLogin() {
        open("http://localhost:9999");
        RegistrationDto userWithIncorrectLogin = getUserWithIncorrectLogin();
        $("[name=login]").setValue(userWithIncorrectLogin.getLogin());
        $("[name=password]").setValue(userWithIncorrectLogin.getPassword());
        $(".button__text").click();
        $(".notification_status_error").waitUntil(visible, 5000);
        $(".notification_visible[data-test-id=error-notification]").shouldHave(Condition.matchesText("Ошибка! Неверно указан логин или пароль"));
    }
}
