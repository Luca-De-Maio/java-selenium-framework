package org.selenium.pom.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.selenium.pom.base.BasePage;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class FlightsPage extends BasePage {
    private final By plusFareSelector = By.xpath("//fare-table-header[@data-e2e='fare-card--plus']/following-sibling::div");
    private final By outboundTickContainer = By.cssSelector("div.outbound--hold-tick");
    private final By bothFlightSelected = By.cssSelector("span._container.icon-50");
    private final By fareTitle = By.cssSelector("h4.fare-selection-summary__title");
    private final By passengerModal = By.tagName("flights-passengers");
    private final By firstName = By.cssSelector("[autocomplete='given-name']");
    private final By lastName = By.cssSelector("[autocomplete='family-name']");
    private final By spinnerOverlay = By.cssSelector("flights-passengers div.spinner__icon");
    private final By continueButton = By.cssSelector("button.continue-flow__button");
    private final Actions actions = new Actions(driver);

    public FlightsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOutboundFlightSelected(){
        scrollIntoValidDate("outbound");

        return driver.findElement(outboundTickContainer).isEnabled();
    }

    public boolean isInboundFlightSelected(){
        scrollIntoValidDate("inbound");

        return driver.findElement(bothFlightSelected).isEnabled();
    }

    public String selectFare() {
        waitForOverlaysToDisappear(spinnerOverlay);
        wait.until(visibilityOfElementLocated(plusFareSelector)).click();
        return driver.findElement(fareTitle).getText().trim();
    }

    private void scrollIntoValidDate(String type){
        int maxAttempts = 5;
        wait.until(invisibilityOf(driver.findElement(By.cssSelector("div.spinner__icon"))));
        boolean buttonClicked = false;
        for (int attempt = 1; attempt <= maxAttempts && !buttonClicked; attempt++) {
            try {
                buttonClicked = attemptToClickButton(type);
            } catch (NoSuchElementException | TimeoutException e) {
                handleExceptionAndClickNextDate(type, attempt);
            }
        }
    }

    private boolean attemptToClickButton(String type) {
        List<WebElement> dateItemPriceButton = driver.findElements(By.cssSelector("[data-ref=\"" + type + "\"] [data-e2e=\"date-item\"]:not(.date-item--disabled)"));
        if (dateItemPriceButton.isEmpty()) throw new NoSuchElementException("Button flight Selector not found");

        for (WebElement dateItemPrice : dateItemPriceButton) {
            wait.until(invisibilityOfAllElements(driver.findElements(By.cssSelector("div.date-item__loader"))));
            dateItemPrice.click();
            WebElement selectButton = driver.findElement(By.cssSelector("[data-e2e=\"flight-card--" + type + "\"] button.ry-button--gradient-blue:not([disabled])"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", selectButton);
            if (selectButton.isEnabled()) {
                System.out.println("Button clicked successfully");
                selectButton.click();
                return true;
            } else {
                throw new NoSuchElementException("Button flight Selector not found");
            }
        }
        return false;
    }


    private void handleExceptionAndClickNextDate(String type, int attempt) {
        WebElement nextDateCarousel = driver.findElement(By.cssSelector("[data-ref=\"" + type + "\"] .carousel-next carousel-arrow"));
        System.out.println("Attempt " + attempt + ": NoSuchElementException occurred");
        wait.until(elementToBeClickable(nextDateCarousel));
        System.out.println("Clicking on next days carousel, attempt: " + attempt);
        nextDateCarousel.click();
    }

    public FlightsPage fillAllPassengersInformation() {
        Actions actions = new Actions(driver);

        actionUtils.scrollToElement(continueButton);

        waitForOverlaysToDisappear(spinnerOverlay);
        wait.until(visibilityOfElementLocated(passengerModal));

        List<WebElement> passengers = driver.findElements(By.tagName("pax-passenger-container"));
        System.out.println(passengers.size());

        Faker faker = new Faker();

        for (int i = 0; i < passengers.size(); i++) {
            WebElement passenger = passengers.get(i);
            try {
                wait.until(elementToBeClickable(passenger));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", passenger);
                fillPassenger(passenger, faker.name().firstName(), faker.name().lastName());
            } catch (StaleElementReferenceException e) {
                // Re-fetch the passenger element if it has gone stale
                passengers = driver.findElements(By.tagName("pax-passenger-container"));
                passenger = passengers.get(i);
                wait.until(elementToBeClickable(passenger));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", passenger);
                fillPassenger(passenger, faker.name().firstName(), faker.name().lastName());
            }
        }
        return this;
    }

    private void fillPassenger(WebElement passenger, String name, String lastname) {
        if (!passenger.findElement(By.cssSelector("span.passenger__type")).getText().trim().equals("Child")) {
            passenger.findElement(By.tagName("button")).click();

            List<WebElement> dropdownItems = driver.findElements(By.cssSelector(".dropdown__menu .dropdown-item__link"));
            for (WebElement dropdownItem : dropdownItems) {
                wait.until(visibilityOf(dropdownItem.findElement(By.cssSelector(".dropdown-item__label"))));
                String labelText = dropdownItem.findElement(By.cssSelector(".dropdown-item__label")).getText().trim();
                if (labelText.equals("Mr")) {
                    dropdownItem.click();
                    break;
                }
            }
        }
        wait.until(elementToBeClickable(firstName)).sendKeys(name);
        wait.until(elementToBeClickable(lastName)).sendKeys(lastname);

        System.out.println("Adding to: " + name + ' ' + lastname + " as passenger ");
    }

    public SeatPage continueToSeatSelectionButton() {
        driver.findElement(continueButton).click();
        return new SeatPage(driver);
    }
}

