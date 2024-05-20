package org.selenium.pom.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatPage extends BasePage {
    private final By familySeatingAlert = By.cssSelector("button.seats-modal__cta");
    private final By availableSeatsAdults = By.cssSelector("button.seatmap__seat:not(.seatmap__seat--unavailable)");
    private final By availableSeatsStandard = By.cssSelector("button.seatmap__seat--standard");
    private final By availableSeatsExceptSelected = By.cssSelector("button.seatmap__seat:not(.seatmap__seat--unavailable):not(.seatmap__seat--selected):not([data-ref$=\"-selected\"])");
    private final By warningChildTeenNotAllowed = By.cssSelector("div.ry-tooltip--warning");
    private final By onHoverInfoAboutTheSeat = By.cssSelector("div.seat-tooltip__info-tooltip");
    private final By nextFlightButton = By.cssSelector("button[data-ref=\"seats-action__button-next\"]");
    private final By activeColumSelectSeats = By.cssSelector("td.passenger-carousel__table-cell-seat--active-column");
    private final By seatOccupied = By.cssSelector("td.passenger-carousel__table-cell-seat--active-column div.seat__seat--occupied");
    private final By continueToBagsButton = By.cssSelector("[data-ref=\"seats-action__button-continue\"]");
    private final By alertFastTrack = By.cssSelector("div.enhanced-takeover-beta__modal .enhanced-takeover-beta__product-confirm-cta");
    private final By spinnerOverlay = By.cssSelector("#main-content div.spinner__icon");
    private final By recommendedButton = By.xpath("//button[contains(text(),\"recommended\")]");
    private static final Map<Character, Character> adjacentRowsMap = new HashMap<>();
    private final Actions actions = new Actions(driver);

    static {
        // Populate the map with adjacent rows
        adjacentRowsMap.put('A',  'B' );
        adjacentRowsMap.put('B',  'C' );
        adjacentRowsMap.put('C',  'D' );
        adjacentRowsMap.put('D',  'E' );
        adjacentRowsMap.put('E',  'F' );
        adjacentRowsMap.put('F',  'E' );
    }

    public SeatPage(WebDriver driver) {
        super(driver);
    }

    public SeatPage load(){
        load("/trip/flights/seats");
        return this;
    }

    public SeatPage acceptFamilySeatingAlert() {
        wait.until(ExpectedConditions.elementToBeClickable(familySeatingAlert)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(familySeatingAlert));
        return this;
    }

    public SeatPage selectSeatsForAdultAndChild() {
        List<WebElement> seatsList = driver.findElements(availableSeatsStandard);

        for (int i = 0; i < seatsList.size() - 1 ; i++) {
            // Assuming you have already obtained seatsList
            WebElement parentSeatElement = seatsList.get(i);
            WebElement childrenSeatElement = seatsList.get(i + 1);

            // Get the ID of the parent's seat
            String seatParentId = parentSeatElement.getAttribute("id");
            String seatChildrenId = childrenSeatElement.getAttribute("id");

            // Extract row and column from the parent's seat ID
            int parentNumber = Integer.parseInt(seatParentId.substring(5, 7));
            char parentLetter = seatParentId.charAt(7);

            // Extract row and column from the children's seat ID
            int childrenNumber = Integer.parseInt(seatChildrenId.substring(5, 7));
            char childrenLetter = seatChildrenId.charAt(7);

            // Check if the parent letter has adjacent rows
            if (hasNextInAdjacentRows(parentLetter, childrenLetter) &&  isSameColumn(childrenNumber, parentNumber)) {
                // Check if the seats are adjacent
                parentSeatElement.click();
                childrenSeatElement.click();
                break;
            }
        }
        return this;
    }

    public SeatPage selectIndividualSeatForAdult() {
        List<WebElement> seatsList = driver.findElements(availableSeatsAdults);
        //seatsList.getFirst().click();

        int passengerWithoutSeat = driver.findElements(activeColumSelectSeats).size() - driver.findElements(seatOccupied).size();

        for(int i = 0; passengerWithoutSeat > 0 && i < seatsList.size(); i++) {
            WebElement seat = seatsList.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seat);
            passengerWithoutSeat--;
        }
        return this;
    }

    public SeatPage selectSeatsForAdultAndTeen() {
        List<WebElement> seatsList = driver.findElements(availableSeatsExceptSelected);
        int passengerWithoutSeat = driver.findElements(activeColumSelectSeats).size() - driver.findElements(seatOccupied).size();

        for(int i = 0; passengerWithoutSeat > 0  && i < seatsList.size(); i++) {
            WebElement seat = seatsList.get(i);
            actions.moveToElement(seat).perform();
            try {
                if (driver.findElement(onHoverInfoAboutTheSeat).isDisplayed()) {
                    seat.click();
                    passengerWithoutSeat--;
                }
            } catch (NoSuchElementException e) {
                // Handle NoSuchElementException, e.g., log a message or take appropriate action
                System.out.println("Warning element not found or not displayed");
            }
        }
        return this;
    }

    public SeatPage clickNextFlightButton() {
        driver.findElement(nextFlightButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(continueToBagsButton)));
        return this;
    }

    // Check if the parent letter has a next element in the adjacent rows
    private static boolean hasNextInAdjacentRows(char parentLetter, char childrenLetter) {
        char adjacentRows = adjacentRowsMap.get(parentLetter);
        return adjacentRows == childrenLetter;
    }

    // Check if two seats are adjacent and not C adjacent to D
    private boolean isSameColumn(int parentRowNumber, int childRowNumber) {
        return parentRowNumber == childRowNumber;
    }

    public void clickContinueButton() {
        try {
            // Click the button that may trigger the alert
            driver.findElement(continueToBagsButton).click();

            // Wait for the alert to be present
            wait.until(ExpectedConditions.visibilityOfElementLocated(alertFastTrack)).click();

        } catch (TimeoutException e) {
            // If the alert is not present within the timeout, catch the exception
            System.out.println("No alert was present after clicking the continue button.");
        }
        //wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(availableSeatsStandard).getFirst()));
        new BagsPage(driver);
    }

    public BagsPage selectSeatsAndContinue() {
        waitForOverlaysToDisappear(spinnerOverlay);
        try {
            // If the recommendation is not present within the timeout, catch the exception
            selectIndividualSeatForAdult();
            clickNextFlightButton();
            selectIndividualSeatForAdult();
            clickContinueButton();
        } catch (TimeoutException e) {
            LOGGER.finest("Recommend seats for flights.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(recommendedButton)).click();
            // Wait for the alert to be present
            wait.until(ExpectedConditions.visibilityOfElementLocated(alertFastTrack)).click();
        }
        return new BagsPage(driver);
    }
}
