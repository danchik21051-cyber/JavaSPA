package com.example;

import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.pages.AppPage;
import com.example.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmokeTest {

    @LocalServerPort
    int port;

    private final LoginPage loginPage = new LoginPage();
    private final AppPage appPage = new AppPage();

    @BeforeAll
    static void setup() {
        Configuration.headless = true;
        Configuration.timeout  = 10_000;
    }

    @BeforeEach
    void init() {
        Configuration.baseUrl = "http://localhost:" + port;
    }

    // ===================== АВТОРИЗАЦИЯ =====================

    @Test
    @DisplayName("Форма логина отображается при открытии")
    void loginElementsIsVisible(){
        Selenide.open("/");
        loginPage.title.shouldBe(visible);
        loginPage.username.shouldBe(visible);
        loginPage.password.shouldBe(visible);
        loginPage.submitBtn.shouldBe(visible);
    }

    @Test
    @DisplayName("Успешный вход: видим приветствие")
    void successfulLogin() {
        Selenide.open("/");
        loginPage.username.setValue("admin");
        loginPage.password.setValue("secret123");
        loginPage.submitBtn.click();
        appPage.welcomeTitle.shouldBe(visible);
        appPage.welcomeTitle.shouldHave(text("Добро пожаловать, admin!"));
    }

    @Test
    @DisplayName("Неверный пароль: видим ошибку")
    void wrongPasswordShowsError() {
        Selenide.open("/");
        loginPage.username.setValue("admin");
        loginPage.password.setValue("wrongpass");
        loginPage.submitBtn.click();
        loginPage.error.shouldHave(text("Неверный логин или пароль"));
        appPage.screen.shouldNotBe(visible);
    }

    @Test
    @DisplayName("Пустые поля: валидация на клиенте")
    void emptyFieldsShowValidationError() {
        Selenide.open("/");
        loginPage.submitBtn.click();
        loginPage.error.shouldHave(text("Заполните оба поля"));
    }

    @Test
    @DisplayName("Кнопка «Выйти» возвращает на форму логина")
    void logoutReturnsToLoginForm() {
        Selenide.open("/");
        loginPage.username.setValue("admin");
        loginPage.password.setValue("secret123");
        loginPage.submitBtn.click();
        appPage.screen.shouldBe(visible);
        appPage.logoutBtn.click();
        loginPage.title.shouldBe(visible);
        appPage.screen.shouldBe(hidden);
    }

    // ===================== ДАННЫЕ =====================

    @Test
    @DisplayName("После логина кнопка «Показать сообщение» работает")
    void loadDataAfterLogin() {
        Selenide.open("/");
        loginPage.username.setValue("admin");
        loginPage.password.setValue("secret123");
        loginPage.submitBtn.click();
        appPage.screen.shouldBe(visible);
        appPage.loadBtn.click();
        appPage.result.shouldBe(visible);
        appPage.result.shouldHave(text("Привет из SPA"));
    }
}