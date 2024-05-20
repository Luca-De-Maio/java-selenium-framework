package org.selenium.pom.pages;

import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.selenium.pom.base.BasePage;

public class ExtrasPage extends BasePage {
    private final By continueAirportTripButton = By.cssSelector("button.airport-and-flight__cta");
    private final By continueToTransportButton = By.cssSelector("button.transport__cta");
    private final Actions actions = new Actions(driver);

    public ExtrasPage(WebDriver driver) {
        super(driver);
    }

    public ExtrasPage clickContinueToTransport() {
        //waitForOverlaysToDisappear();
        actionUtils.scrollToBottomOfPage();
        actionUtils.click(continueAirportTripButton);
        return this;
    }

    public ReviewAndPayPage clickContinueToReviewAndPay() {
        actionUtils.scrollToBottomOfPage();

        actionUtils.click(continueToTransportButton);

        return new ReviewAndPayPage(driver);
    }
}
