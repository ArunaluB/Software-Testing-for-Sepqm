package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class HomeSubscriptionTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        // Updated ChromeDriver path
        System.setProperty("webdriver.chrome.driver",
                "D:\\3rdyear2nd\\Y3S2Avindi\\DS\\notification\\chromedriver-win32\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testSubscriptionWithInvalidEmail() {
        driver.get("https://automationexercise.com");

        WebElement emailInput = driver.findElement(By.id("susbscribe_email")); // note: id is intentionally 'susbscribe_email'
        emailInput.sendKeys("1235"); // invalid email input

        WebElement subscribeButton = driver.findElement(By.id("subscribe"));
        subscribeButton.click();

        // Check HTML5 validation using JS
        boolean isValid = (boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].checkValidity();", emailInput);

        Assert.assertFalse(isValid, "Expected email input to be invalid");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
