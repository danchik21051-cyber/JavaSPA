package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

/**
 * Page Object для экрана после авторизации.
 */
public class AppPage {

    // ---------- Локаторы ----------
    private final SelenideElement welcomeTitle = $("#welcome-title");
    private final SelenideElement loadBtn     = $("#load-btn");
    private final SelenideElement result      = $("#result");
    private final SelenideElement logoutBtn   = $("#logout-btn");
    private final SelenideElement screen      = $("#app-screen");

    // ---------- Действия ----------

    /** Нажимает "Загрузить данные" */
    public AppPage clickLoadData() {
        loadBtn.click();
        return this;
    }

    /** Нажимает "Выйти" */
    public LoginPage clickLogout() {
        logoutBtn.click();
        return new LoginPage();   // ← возвращаем ДРУГОЙ Page Object
    }

    // ---------- Проверки ----------

    public AppPage shouldBeVisible() {
        screen.shouldBe(visible);
        return this;
    }

    public AppPage shouldBeHidden() {
        screen.shouldBe(hidden);
        return this;
    }

    public AppPage shouldShowWelcome(String expected) {
        welcomeTitle.shouldHave(text(expected));
        return this;
    }

    public AppPage shouldShowResult(String expected) {
        result.shouldHave(text(expected));
        return this;
    }
}