package org.selenium.pom.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.LoginPage;
import org.selenium.pom.pages.SeatPage;

public class MyHeader extends BasePage {
    private final By loginButton = By.xpath("//button[contains(text(),'Log in')]");

    public MyHeader(WebDriver driver) {
        super(driver);
    }


    public LoginPage clickOnLoginLink(){
        driver.findElement(loginButton).click();
         // Index starts from 0

        return new LoginPage(driver);
    }
}
