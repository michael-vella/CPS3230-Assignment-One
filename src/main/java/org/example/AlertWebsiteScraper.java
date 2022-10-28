package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class AlertWebsiteScraper {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected List<Alert> alerts = new LinkedList<>();
    String userId = "c55bc56a-232c-46a4-9778-7f0d41690aa2";

    String loginXPath = "//div[@class='navbar-collapse collapse d-sm-inline-flex justify-content-between']//li[2]//a";
    String userIdId = "UserId";
    String validLoginXPath = "//main[@class='pb-3']//h1";

    public boolean result;
    public boolean resultTwo;
    public boolean resultThree;
    public boolean resultFour;
    public boolean resultFive;
    public boolean resultSix;

    // Mock Alerts
    Alert carAlert = new Alert(
            1,
            "Lot 14 Metal DieCast model car",
            "Lot 14 Metal DieCast model car",
            "https://www.maltapark.com/item/details/9514895",
            "https://www.maltapark.com/asset/itemphotos/9514895/9514895_1.jpg?_ts=1",
            400
    );

    Alert boatAlert = new Alert(
            2,
            "Trimarchi 57 S. - Tohatsu 115 hp",
            "Trimarchi 57 S (5.70 mt.) Year 2021 ° with Tohatsu 115 hp engine year 2022 ° with only 30 hours of motion",
            "https://www.maltapark.com/item/details/9516448",
            "https://www.maltapark.com/asset/itemphotos/9516448/9516448_1.jpg?_ts=4",
            2400000
    );

    Alert rentAlert = new Alert(
            3,
            "Property For Rent - SLIEMA ONE BEDROOM BARGAIN",
            "ONE BEDROOM/ ONE BATHROOM/ PRIME LOCATION",
            "https://www.maltapark.com/item/details/9514948",
            "https://www.maltapark.com/asset/itemphotos/9514948/9514948_1.jpg?_ts=1",
            85000
    );

    Alert saleAlert = new Alert(
            4,
            "Townhouse",
            "FOR SALE",
            "https://www.maltapark.com/item/details/9514599",
            "https://www.maltapark.com/asset/itemphotos/9514599/9514599_9.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=9",
            26500000
    );

    Alert toysAlert = new Alert(
            5,
            "Trolls Soft Toy",
            "Trolls Soft Toy - New",
            "https://www.maltapark.com/item/details/9516601",
            "https://www.maltapark.com/asset/itemphotos/9516601/9516601_1.jpg?_ts=1",
            700
    );

    Alert electronicsAlert = new Alert(
            6,
            "Laptop Power Adapters",
            "Laptop Power Adapters",
            "https://www.maltapark.com/item/details/9509385",
            "https://www.maltapark.com/asset/itemphotos/9509385/9509385_4.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=4",
            1500
    );

    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver", "C:/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void validLogin(){
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginXPath)));
        driver.findElement(By.xpath(loginXPath)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userIdId)));
        WebElement element =  driver.findElement(By.id(userIdId));
        element.sendKeys(userId);
        element.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(validLoginXPath)));
        if (driver.findElement(By.xpath(validLoginXPath)).getText().equals("Latest alerts for Michael Vella")){
            result = true;
        } else {
            result = false;
        }
        driver.quit();
    }

    public void invalidLogin(){
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginXPath)));
        driver.findElement(By.xpath(loginXPath)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userIdId)));
        WebElement element =  driver.findElement(By.id(userIdId));
        element.sendKeys("Invalid Login Credentials");
        element.sendKeys(Keys.ENTER);

        if (driver.getCurrentUrl().equals("https://www.marketalertum.com/Alerts/Login")){
            result = true;
        } else {
            result = false;
        }
        driver.quit();
    }

    public void uploadNAlerts(int n){
        for (int i = 0; i < n; i++){
            alerts.add((carAlert));
        }
    }
}
