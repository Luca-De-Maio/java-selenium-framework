package org.selenium.pom.factory.abstractFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class ChromeDriverManagerAbstract extends DriverManagerAbstract {

    @Override
    protected void startDriver() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("headless=true");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
