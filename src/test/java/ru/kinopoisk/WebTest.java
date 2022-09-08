package ru.kinopoisk;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @ValueSource(strings = {"Filth", "The Departed"})
    @ParameterizedTest(name = "Результаты поиска не пустые для {0}")
    void kinopoiskTest(String testData, String expectedResult) {
        Selenide.open("https://www.kinopoisk.ru/");
        $(byName("kp_query")).setValue(testData).pressEnter();
        $(byLinkText(testData)).shouldHave(Condition.text(testData));
    }

    @CsvSource(value = {"Filth, Джеймс МакЭвой",
            "The Departed, Леонардо ДиКаприо"
    })
    @ParameterizedTest(name = "Результаты поиска отдает {1} для запроса {0}")
    void kinopoiskCompleteTest(String testData, String expectedResult) {
        Selenide.open("https://www.kinopoisk.ru/");
        $(byName("kp_query")).setValue(testData).pressEnter();
        $(byLinkText(expectedResult)).shouldHave(Condition.text(expectedResult));
    }

    static Stream<Arguments> simbirsoftMenuTest() {
        return Stream.of(
                Arguments.of("RU", List.of("Проекты", "Услуги","" ,"" ,"" ,"" ,"" ,"" ,"" ,"" ,"" ,
                        "Блог", "Вакансии", "Карьера","" ,"" , "О нас","" ,"" , "Контакты")),
                Arguments.of("EN", List.of("Projects", "Services","" ,"" ,"" , "Insights", "About Us","" ,"" , "Locations"))
        );
    }
    @MethodSource
    @ParameterizedTest(name = "Для локали {0} отображаются кнопки {1}")
    void simbirsoftMenuTest(String lang, List<String> expectedButtons) {
        Selenide.open("https://www.simbirsoft.com/");
        if (lang == "RU") {
            $$(".gh-nav-item a").shouldHave(CollectionCondition.texts(expectedButtons));
        } else {
            $$(".gh-tools a").first().click();
            $$(".gh-nav-item a").shouldHave(CollectionCondition.texts(expectedButtons));
        }
    }
    @EnumSource(Lang.class)
    @ParameterizedTest(name = "Для локали {0} отображается иконка")
    void simbirsoftMenuTestIcon(Lang lang) {
        Selenide.open("https://www.simbirsoft.com/");
        if (lang.name() == "RU") {
            $(".gh-logo").shouldBe(Condition.visible);
        } else {
            $$(".gh-tools a").first().click();
            $(".gh-logo").shouldBe(Condition.visible);

        }
    }
}



