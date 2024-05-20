package org.selenium.pom.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ActionUtils(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public void enterText(By selector, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        element.clear();
        element.sendKeys(text);
    }

    public void click(By selector) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selector));
        element.click();
    }

    public void selectByVisibleText(By selector, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(visibleText);
    }

    public void selectByValue(By selector, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
    }

    public void selectByIndex(By selector, int index) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        Select dropdown = new Select(element);
        dropdown.selectByIndex(index);
    }

    public WebElement waitForVisibility(By selector) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public WebElement waitForClickability(By selector) {
        return wait.until(ExpectedConditions.elementToBeClickable(selector));
    }

    public void scrollToElement(By scrollContainer, By elementSelector) {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(scrollContainer));
        WebElement element = null;

        while (element == null) {
            try {
                element = container.findElement(elementSelector);
                if (element.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                // Scroll down
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(0, 100);", container);
            }
        }

        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void clickInVirtualScroll(By scrollContainer, By elementSelector) {
        scrollToElement(scrollContainer, elementSelector);
        click(elementSelector);
    }

    public void forceClick(By selector) {
        scrollToElement(selector);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(selector));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(By selector) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void selectCustomDropdown(By dropdownToggle, By dropdownOption) {
        // Click the dropdown to open it
        WebElement toggleElement = wait.until(ExpectedConditions.elementToBeClickable(dropdownToggle));
        toggleElement.click();

        // Select the desired option
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(dropdownOption));
        optionElement.click();
    }
    public String getText(By selector) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return element.getText();
    }

    public void scrollToBottomOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
