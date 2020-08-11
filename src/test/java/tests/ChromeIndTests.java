package tests;

import com.codeborne.selenide.Configuration;
import helpers.AttachmentsHelper;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;
import static java.lang.Integer.parseInt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

@Epic("Chrome Industries UI tests")
@Feature("Check price list item price - Vega Brief 2.0")
@Story("Check price for Vega 2.0 bag test")
@Tag("chrome_ind_tests")
class ChromeIndTests extends TestBase {
    @Test
    @AllureId("682")
    @DisplayName("Navigate from main page to Vega 2.0 Brief and check its price")
    @Story("Check price for Vega 2.0 bag test")
    @Feature("Check price list item price - Vega Brief 2.0")
    @Description("Open main page, " +
            "select shipment to Russia" +
            "navigate to bags => laptop bags" +
            "find Vega 2.0. Transit bag" +
            "check for price against the value from command line -Dcheck_price")
    void checkVegaBriefPrice() {
        step ("Open " + url, () -> {
            open(url);
        });
        step("Check pop-up \'We ship to\'", () -> {
            $("div.glPopupContent").shouldBe(visible);
        });
        step("Click \'Change your shipping country\'", () -> {
            $("[data-action='ShippingSwitcher']").click();
        });
        step("Click country selector, select country by list value => RU" +
                "Check if Currency is set to RUB", () -> {
            $("#gle_selectedCountry").click();
            $(by("value", "RU")).click();
            $("#gle_selectedCurrency").shouldHave(value("RUB"));
        });
        step("Click SAVE on country selector" +
                "check that country flag in the upper right corner is set" +
                "to Russian", () -> {
            $("[data-key='SavenClose']").click();
            $("#shippingSwitcherLink").shouldHave(cssClass("flag-ru"));
            $("ul.nav-container").shouldHave(text("Bags"));
        });
        step("Get rid of cookies consent, if class notice--hide is displayed, then click it", () -> {
            if ($(".notice--hide").isDisplayed()){
                $(".notice--hide").click();
            }
        });
        step("Check if navigation bar has /'Bags/'", () -> {
            $("ul.nav-container").shouldHave(text("Bags"));
        });
        step("Hover the main menu over \'Bags\' laptop should be present in the drop down", () -> {
            $$("ul.nav-container li").findBy(text("Bags")).hover();
            $$("div.columns-4.sub-cat-menu-item.sub-cat-menu-list").find(text("laptop bags"));
        });
        step("Go to laptop bags collection, Vega should be present on the page", () -> {
            $("[data-gtm-link='bags | laptop bags']").click();
            $$(".product-card--name").find(value("Vega"));
        });
        step("Go Vega Brief page, price should be greater or equal to " + checkPrice, () -> {
            $(byText("Vega 2.0 Transit Brief")).parent().parent().parent().scrollIntoView(true);
            $(byText("Vega 2.0 Transit Brief")).click();
            String vegaTextVegaPrice = $(".product-price span").innerText();
            AttachmentsHelper.attachAsText("Current price on page", vegaTextVegaPrice);
            Integer vegaIntPrice = parseInt(vegaTextVegaPrice.split(",")[0]);
            assertThat(vegaIntPrice, is(greaterThanOrEqualTo(parseInt(checkPrice))));
        });
    }

}

