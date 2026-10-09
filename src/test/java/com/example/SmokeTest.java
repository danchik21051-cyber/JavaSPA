package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.pages.AppPage;
import com.example.pages.LoginPage;
import org.junit.jupiter.api.*;

class SmokeTest {

    private LoginPage loginPage;
    private AppPage   appPage;

    @BeforeAll
    static void setup() {
        Configuration.headless = true;
        Configuration.timeout  = 10_000;
        Configuration.baseUrl  = "http://localhost:8080";
    }

    @BeforeEach
    void init() {
        loginPage = new LoginPage();
        appPage   = new AppPage();
    }

    // ===================== АВТОРИЗАЦИЯ =====================

    @Test
    @DisplayName("Форма логина отображается при открытии")
    void loginFormIsVisible() {
        loginPage.open()
                .shouldHaveTitle("Вход в систему")
                .shouldBeVisible();
    }

    @Test
    @DisplayName("Успешный вход: видим приветствие")
    void successfulLogin() {
        loginPage.open()
                .loginAs("admin", "secret123");

        appPage.shouldBeVisible()
                .shouldShowWelcome("Добро пожаловать, admin!");
    }

    @Test
    @DisplayName("Неверный пароль: видим ошибку")
    void wrongPasswordShowsError() {
        loginPage.open()
                .loginAs("admin", "wrongpass")
                .shouldShowError("Неверный логин или пароль");

        appPage.shouldBeHidden();
    }

    @Test
    @DisplayName("Пустые поля: валидация на клиенте")
    void emptyFieldsShowValidationError() {
        loginPage.open()
                .clickSubmit()
                .shouldShowError("Заполните оба поля");
    }

    @Test
    @DisplayName("Кнопка «Выйти» возвращает на форму логина")
    void logoutReturnsToLoginForm() {
        loginPage.open()
                .loginAs("admin", "secret123");

        appPage.shouldBeVisible();

        // clickLogout() возвращает LoginPage — сразу проверяем форму
        loginPage = appPage.clickLogout();
        loginPage.shouldBeVisible();
        appPage.shouldBeHidden();
    }

    // ===================== ДАННЫЕ =====================

    @Test
    @DisplayName("После логина кнопка «Загрузить данные» работает")
    void loadDataAfterLogin() {
        loginPage.open()
                .loginAs("admin", "secret123");

        appPage.clickLoadData()
                .shouldShowResult("Привет из SPA!");
    }

    @AfterAll
    static void tearDown() {
        Selenide.closeWebDriver();
    }
}