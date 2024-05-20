package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.selenium.pom.base.BasePage;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BagsPage extends BasePage {
    private final By continueToExtraButton = By.cssSelector("[data-ref=\"bags-continue-button\"]");
    private final By smallBagRadioButton = By.cssSelector("div[data-ref=\"cabin-bag-expanded\"] ry-radio-circle-button");
    private final Actions actions = new Actions(driver);
    private final By spinnerOverlay = By.cssSelector("#main-content div.spinner__icon");



    public BagsPage(WebDriver driver) {
        super(driver);
    }

    public BagsPage selectSmallBag() {
        waitForOverlaysToDisappear(spinnerOverlay);

        wait.until(elementToBeClickable(smallBagRadioButton));
        driver.findElements(smallBagRadioButton).get(1).click();
        return this;
    }

    public ExtrasPage clickContinueButton() {
        waitForOverlaysToDisappear(spinnerOverlay);
        actionUtils.scrollToElement(continueToExtraButton);
        actionUtils.click(continueToExtraButton);

        return new ExtrasPage(driver);
    }
}
