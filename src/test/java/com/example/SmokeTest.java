package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

class SmokeTest {

    @BeforeAll
    static void setup() {
        Configuration.headless  = true;
        Configuration.timeout   = 10_000;
        Configuration.baseUrl   = "http://localhost:8080";
    }

    // ===================== АВТОРИЗАЦИЯ =====================

    @Test
    @DisplayName("Форма логина отображается при открытии")
    void loginFormIsVisible() {
        open("/");
        $("#login-title").shouldHave(text("Вход в систему"));
        $("#username").shouldBe(visible);
        $("#password").shouldBe(visible);
        $("#submit-btn").shouldBe(visible);
    }

    @Test
    @DisplayName("Успешный вход: видим приветствие")
    void successfulLogin() {
        open("/");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("#submit-btn").click();

        // Экран логина исчез, экран приложения появился
        $("#login-screen").shouldBe(hidden);
        $("#app-screen").shouldBe(visible);
        $("#welcome-title").shouldHave(text("Добро пожаловать, admin!"));
    }

    @Test
    @DisplayName("Неверный пароль: видим ошибку")
    void wrongPasswordShowsError() {
        open("/");
        $("#username").setValue("admin");
        $("#password").setValue("wrongpass");
        $("#submit-btn").click();

        $("#login-error")
                .shouldBe(visible)
                .shouldHave(text("Неверный логин или пароль"));

        // Экран приложения НЕ должен появиться
        $("#app-screen").shouldBe(hidden);
    }

    @Test
    @DisplayName("Пустые поля: валидация на клиенте")
    void emptyFieldsShowValidationError() {
        open("/");
        $("#submit-btn").click();

        $("#login-error")
                .shouldBe(visible)
                .shouldHave(text("Заполните оба поля"));
    }

    @Test
    @DisplayName("Кнопка «Выйти» возвращает на форму логина")
    void logoutReturnsToLoginForm() {
        open("/");
        // Сначала логинимся
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("#submit-btn").click();
        $("#app-screen").shouldBe(visible);

        // Выходим
        $("#logout-btn").click();
        $("#login-screen").shouldBe(visible);
        $("#app-screen").shouldBe(hidden);
    }

    // ===================== СТАРЫЕ ТЕСТЫ =====================

    @Test
    @DisplayName("После логина кнопка «Загрузить данные» работает")
    void loadDataAfterLogin() {
        open("/");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("#submit-btn").click();

        $("#load-btn").click();
        $("#result").shouldHave(text("Привет из SPA!"));
    }

    @Test
    @DisplayName("API /api/hello отдаёт JSON")
    void apiHelloReturnsOk() {
        open("/api/hello");
        $("body").shouldHave(text("Привет из SPA!"));
    }

    @AfterAll
    static void tearDown() {
        Selenide.closeWebDriver();
    }
}