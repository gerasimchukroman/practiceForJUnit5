package ru.kinopoisk;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static java.nio.channels.SocketChannel.open;

public class WebTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }
    @ValueSource(strings = {"Filth", "The Departed"})
    @ParameterizedTest(name = "Результаты поиска не пустые для {0}")
    void kinopoiskTest(String testData) {
        Selenide.open("https://www.kinopoisk.ru/");
        $(byName("kp_query")).setValue(testData).pressEnter();
        $(byLinkText(testData)).shouldHave(Condition.text(testData));
    }
}
