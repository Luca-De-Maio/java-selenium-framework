package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class LoginPage extends BasePage {

    private final By emailAddressField = By.cssSelector("input[name=\"email\"]");
    private final By passwordAddressField = By.cssSelector("input[name=\"password\"]");
    private final By loginButton = By.cssSelector("button[type=\"submit\"]");
    private final By iframeLogin = By.tagName("iframe");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        switchToIframe();
        driver.findElement(emailAddressField).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        driver.findElement(passwordAddressField).sendKeys(password);
        return this;
    }

    public HomePage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        switchToDefaultContent();
        return new HomePage(driver);
    }

    private boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    // Utility methods
    private void switchToIframe() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(iframeLogin));
        driver.switchTo().frame(driver.findElement(iframeLogin));
    }

    private void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        waitForOverlaysToDisappear(By.tagName("iframe"));
    }

}
