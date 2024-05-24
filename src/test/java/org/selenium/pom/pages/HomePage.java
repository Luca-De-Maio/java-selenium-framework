package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.MyHeader;
import org.selenium.pom.pages.components.BookingSelector;

public class HomePage extends BasePage {
    private final MyHeader myHeader;
    private final BookingSelector bookingSelector;
    private final By alertCookies = By.cssSelector("#cookie-popup-with-overlay button[data-ref=\"cookie.accept-all\"]");

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public BookingSelector getBookingSelector() {
        return bookingSelector;
    }

    public HomePage(WebDriver driver) {
        super(driver);
        myHeader = new MyHeader(driver);
        bookingSelector = new BookingSelector(driver);
    }

    public HomePage load(){
        load("/");
        return this;
    }

    public HomePage acceptCookies() {
        actionUtils.click(alertCookies);
        return this;
    }

}
