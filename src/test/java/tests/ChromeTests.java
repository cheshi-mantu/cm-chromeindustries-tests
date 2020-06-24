package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;

@Epic("QA.GURU QA automation course")
@Story("Selenide Chromeindustries check price tests")
@Tag("chrome_tests")
class ChromeTests extends TestBase {
//    @BeforeEach
//    void MaxBrowserWindow(){
//        Configuration.startMaximized = true;
//    }
    @Test
    @Description("Open page, find Вклады button, select closest A, then click")
    @DisplayName("Open main page, click on Вклады button by closest A")
    void mainPageSelectCountry() {
//        Configuration.browser = "opera";
        step ("Open chrome main page", () -> {
            open(url);
        });
        step("Check pop-up \'We ship to\'", () -> {
            $("#globale_popup").shouldBe(visible);
        });
        step("Click \'Change your shipping country\'", () -> {
            $("[data-action='ShippingSwitcher']").click();
        });
        step("Click country selector, select currency", () -> {
            $(byId("gle_selectedCountry")).click();
            $(by("value", "RU")).click();
            $(byId("gle_selectedCurrency")).shouldHave(text("Russian Ruble"));
        });
        step("Click SAVE", () -> {
            $("[data-key='SavenClose']").click();
            $("#shippingSwitcherLink").shouldHave(cssClass("flag-ru"));
            $("ul.nav-container li a").shouldHave(text("Bags"));
        });
        step("Go to heritage bags", () -> {
            $("ul.nav-container li a").shouldHave(text("Bags")).hover();
            $(".columns-4.sub-cat-menu-item.sub-cat-menu-list").shouldHave(text("treadwell"));
            $("[data-gtm-link='treadwell travel collection']").click();
            $(".grid-col").shouldHave(text("Vega 2.0 Transit Brief"));
        });
        step("Go to heritage bags", () -> {
            $$(".product-card--content").find(text("Vega 2.0 Transit Brief")).click();
            $(".desc").shouldHave(text(checkPrice));
        });

    }

}

