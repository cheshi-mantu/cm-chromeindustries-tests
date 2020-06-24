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

@Epic("Chrome Industries UI tests")
@Story("Selenide Chrome industries check price for Vega 2.0 bag test")
@Tag("chrome_ind_tests")
class ChromeIndTests extends TestBase {
    @Test
    @Description("Open main page, " +
            "select shipment to Russia" +
            "navigate to treadwell collection" +
            "find Vega 2.0. Transit bag" +
            "check for price against the value from command line -Dcheck_price")
    @DisplayName("Navigate from main page to Vega 2.0 Brief")
    void mainPageSelectCountry() {
//        Configuration.browser = "opera";
        step ("Open " + url, () -> {
            open(url);
        });
        step("Check pop-up \'We ship to\'", () -> {
//            $("#globale_popup").shouldBe(visible);
            $("div.glPopupContent").shouldBe(visible);
        });
        step("Click \'Change your shipping country\'", () -> {
            $("[data-action='ShippingSwitcher']").click();
        });
        step("Click country selector, select country by list value => RU" +
                "Check if Currency is set to RUB", () -> {
            $(byId("gle_selectedCountry")).click();
            $(by("value", "RU")).click();
            $(byId("gle_selectedCurrency")).shouldHave(value("RUB"));
        });
        step("Click SAVE on country selector" +
                "check that country flag in the upper right corner is set" +
                "to Russian", () -> {
            $("[data-key='SavenClose']").click();
            $("#shippingSwitcherLink").shouldHave(cssClass("flag-ru"));
            $("ul.nav-container li a").shouldHave(text("Bags"));
        });
        step("Check if navigation bar has /'Bags/'", () -> {
            $("ul.nav-container li a").shouldHave(text("Bags"));
        });
        step("Hover the main menu over \'Bags\' treadwel should be present in the drop down", () -> {
            $("ul.nav-container li a").shouldHave(text("Bags")).hover();
            $(".columns-4.sub-cat-menu-item.sub-cat-menu-list").shouldHave(text("treadwell"));
        });
        step("Go to treadwell bags collection, Vega should be present on the page", () -> {
            $("[data-gtm-link='treadwell travel collection']").click();
            $$(".product-card--name").find(value("Vega"));
        });
        step("Go Vega Brief page, price should be " + checkPrice, () -> {
//            $$("a").findBy(text("Vega 2.0 Transit Brief")).click();
            $(byText("Vega 2.0 Transit Brief")).click();
            $(".desc").shouldHave(text(checkPrice));
        });

    }

}

