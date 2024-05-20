package org.selenium.pom.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.ActionUtils;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class BasePage {
    protected static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName()); // Logger initialization
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ActionUtils actionUtils; // Declare SelectorUtils

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actionUtils = new ActionUtils(driver, 15); // Initialize SelectorUtils with a 10-second timeout
    }

    public void load(String endPoint) {
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
    }

    public void waitForOverlaysToDisappear(By overlay) {
        List<WebElement> overlays = driver.findElements(overlay);
        LOGGER.info("Spinner/overlay total: " + overlays.size());
        if (!overlays.isEmpty()) {
            wait.until(ExpectedConditions.invisibilityOfAllElements(overlays));
            LOGGER.info("Spinner/overlay Invisible");
        } else {
            LOGGER.info("Spinner/overlay not found");
        }
    }
}
