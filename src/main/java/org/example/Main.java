package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String args[]) {
        WebDriver driver;
        WebDriverWait wait;

        System.setProperty("webdriver.chrome.driver", "C:/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.maltapark.com/");
        driver.manage().window().maximize();

        WebElement element = driver.findElement(By.id("okbutton"));
        while (!element.getText().equals("Close")) {
            element = driver.findElement(By.id("okbutton"));
        }
        element.click();

        WebElement searchField = driver.findElement(By.id("search"));
        searchField.sendKeys("Car");
        searchField.sendKeys(Keys.ENTER);
    }
}
