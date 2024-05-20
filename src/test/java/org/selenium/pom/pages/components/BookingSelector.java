package org.selenium.pom.pages.components;

import org.openqa.selenium.*;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.FlightsPage;

import java.awt.print.Book;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BookingSelector extends BasePage {
    private final By inputDepartureField = By.id("input-button__departure");
    private final By countriesSpan = By.cssSelector("span[data-ref=\"country__name\"]");
    private final By airportSpan = By.cssSelector(".airport-item");
    private final By destinationContainer = By.tagName("fsw-destination-container");
    private final By datePickerCalendarContainer = By.tagName("ry-tooltip");
    private final By daysAvailable = By.cssSelector("div.calendar-body__cell:not(.calendar-body__cell--disabled)");

    private final By searchButton = By.cssSelector("[aria-label=\"Search\"]");

    //private final By adultPassenger

    public BookingSelector(WebDriver driver) {
        super(driver);
    }

    private By getAddToCartBtnElement(){
        return By.id("input-button__departure");
    }

    public BookingSelector selectDeparture(String country, String city) {
        wait.until(elementToBeClickable(driver.findElement(inputDepartureField)));
        driver.findElement(inputDepartureField).click();
        selectCountry(country);
        selectAirport(city);
        return this;
    }

    public BookingSelector selectDestination(String country, String city){
        wait.until(elementToBeClickable(driver.findElement(destinationContainer)));
        selectCountry(country);
        selectAirport(city);
        return this;
    }

    private void selectCountry(String country) {
        wait.until(visibilityOfElementLocated(By.cssSelector(".countries__country-inner")));
        List<WebElement> countryElements = driver.findElements(By.cssSelector(".countries__country-inner"));
        List<String> countryNames = countryElements.stream().map(WebElement::getText).toList();

        countryElements.get(countryNames.indexOf(country)).click();
    }

    private void selectAirport(String city){
        List<WebElement> airportsWebElements = driver.findElements(airportSpan);
        List<String> airportsNames = airportsWebElements.stream().map(WebElement::getText).toList();
        WebElement airportWebElement = airportsWebElements.get(airportsNames.indexOf(city));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", airportWebElement);
        airportWebElement.click();
    }

    public BookingSelector selectFirstAvailableDateForDepartReturn() {
        wait.until(visibilityOfElementLocated(daysAvailable));
        driver.findElements(daysAvailable).getFirst().click();
        return this;
    }

    public BookingSelector selectLastAvailableDateForReturn() {
        wait.until(visibilityOfElementLocated(daysAvailable));
        driver.findElements(daysAvailable).getLast().click();
        return this;
    }

    public BookingSelector selectPassengers(String passengerType, int numPassengers) {
        WebElement passengerTypeWebElement = driver.findElement(By.cssSelector("[data-ref='passengers-picker__" + passengerType + "']"));
        int passengerCounter = Integer.parseInt(passengerTypeWebElement.findElement(By.cssSelector("div.counter__value")).getText().trim());
        while (passengerCounter < numPassengers) {
            System.out.println(passengerCounter);
            passengerTypeWebElement.findElement(By.cssSelector("[data-ref=\"counter.counter__increment\"]")).click();
            passengerCounter ++;
        }
        return this;
    }

    public FlightsPage clickOnSearchButton(){
        wait.until(visibilityOfElementLocated(searchButton));
        driver.findElement(searchButton).click();
        return new FlightsPage(driver);
    };
}
