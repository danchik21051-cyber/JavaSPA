package com.example.pages;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class AppPage {

    public final SelenideElement welcomeTitle = $("#welcome-title");
    public final SelenideElement loadBtn     = $("#load-btn");
    public final SelenideElement result      = $("#result");
    public final SelenideElement logoutBtn   = $("#logout-btn");
    public final SelenideElement screen      = $("#app-screen");

}