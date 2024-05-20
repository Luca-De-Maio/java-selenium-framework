package org.selenium.pom.base;

import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.abstractFactory.DriverManagerAbstract;
import org.selenium.pom.factory.abstractFactory.DriverManagerFactoryAbstract;
import org.selenium.pom.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public abstract class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private final ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private void setDriverManager(DriverManagerAbstract driverManager) {
        this.driverManager.set(driverManager);
    }

    protected DriverManagerAbstract getDriverManager() {
        return this.driverManager.get();
    }

    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    protected WebDriver getDriver() {
        return this.driver.get();
    }

    @Parameters("browser")
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser) {
        browser = System.getProperty("browser", browser);
        if (browser == null) browser = "CHROME";
        setDriverManager(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)));
        setDriver(getDriverManager().getDriver());
        LOGGER.info("Started driver for browser: " + browser);
    }

    @AfterMethod
    public synchronized void quitDriver(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            File screenshot = new File("screenshots" + File.separator + result.getName() + ".png");
            try {
                takeScreenshot(screenshot);
            } catch (IOException e) {
                LOGGER.severe("Failed to capture screenshot: " + e.getMessage());
            }
        }
        getDriver().quit();
        LOGGER.info("Quit driver");
    }

    public void injectCookiesToBrowser(Cookies cookies) {
        List<Cookie> seleniumCookies = new CookieUtils().convertRestAssuredCookiesToSeleniumCookies(cookies);
        for (Cookie cookie : seleniumCookies) {
            LOGGER.info(cookie.toString());
            getDriver().manage().addCookie(cookie);
        }
    }
    private void takeScreenshot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }

    private void takeScreenshotUsingAShot(File destFile) {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());
        try {
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        } catch (IOException e) {
            LOGGER.severe("Failed to capture AShot screenshot: " + e.getMessage());
        }
    }
}
