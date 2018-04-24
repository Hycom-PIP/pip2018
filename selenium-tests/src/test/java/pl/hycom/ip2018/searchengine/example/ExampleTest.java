package pl.hycom.ip2018.searchengine.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ExampleTest {

    private WebDriver driver;

    @Before
    public void before() {
        String exePath = "webdrivers/binaries/windows/googlechrome/64bit/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.p.lodz.pl/pl");
    }

    @Test
    public void test() {
        driver.findElement(By.id("NewsletterButton")).click();
        boolean isSomeElementPresent = driver.findElements(By.id("block-btlock-27")).size() > 0;
        assertTrue(isSomeElementPresent);
    }

    @After
    public void after() {
        driver.quit();
    }
}
