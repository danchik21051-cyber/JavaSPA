package com.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement title     = $("#login-title");
    private final SelenideElement username  = $("#username");
    private final SelenideElement password  = $("#password");
    private final SelenideElement submitBtn = $("#submit-btn");
    private final SelenideElement error     = $("#login-error");

    // ---------- Действия ----------

    public LoginPage open() {
        Selenide.open("/");          // ← явно через класс, не конфликтует
        return this;
    }

    public LoginPage setUsername(String value) {
        username.setValue(value);
        return this;
    }

    public LoginPage setPassword(String value) {
        password.setValue(value);
        return this;
    }

    public LoginPage clickSubmit() {
        submitBtn.click();
        return this;
    }

    public LoginPage loginAs(String user, String pass) {
        setUsername(user);
        setPassword(pass);
        clickSubmit();
        return this;
    }

    // ---------- Проверки ----------

    public LoginPage shouldHaveTitle(String expected) {
        title.shouldHave(Condition.text(expected));
        return this;
    }

    public LoginPage shouldShowError(String expected) {
        error.shouldBe(Condition.visible)
                .shouldHave(Condition.text(expected));
        return this;
    }

    public LoginPage shouldBeVisible() {
        title.shouldBe(Condition.visible);
        return this;
    }
}