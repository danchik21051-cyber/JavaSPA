package com.example.pages;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public final SelenideElement title     = $("#login-title");
    public final SelenideElement username  = $("#username");
    public final SelenideElement password  = $("#password");
    public final SelenideElement submitBtn = $("#submit-btn");
    public final SelenideElement error     = $("#login-error");

}