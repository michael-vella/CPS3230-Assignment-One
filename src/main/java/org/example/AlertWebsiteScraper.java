package org.example;

import com.google.gson.Gson;
import org.example.Utils.ApiService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlertWebsiteScraper {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected List<Alert> alerts = new LinkedList<>();

    protected Request request;
    protected String jsonString;

    String userId = "c55bc56a-232c-46a4-9778-7f0d41690aa2";

    String loginXPath = "//div[@class='navbar-collapse collapse d-sm-inline-flex justify-content-between']//li[2]//a";
    String userIdId = "UserId";
    String validLoginXPath = "//main[@class='pb-3']//h1";
    String iconXPath = "//table//tbody//tr[1]//td//h4//img";
    String headingXPath = "//table//tr[1]//h4";
    String descriptionXPath = "//table//tr[3]//td";
    String imageXPath = "//table//tr[2]//td//img";
    String priceXPath = "//table//tr[4]//td";
    String linkXPath = "//table//tr[5]//a";
    String alertsXPath = "//table";

    public boolean result;

    public int resultOne;
    public int resultTwo;
    public int resultThree;
    public int resultFour;
    public int resultFive;
    public int resultSix;
    public int resultAlertCount;

    public String resultIcon;

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
        System.setProperty("webdriver.chrome.driver", "E:/Chromedriver/chromedriver.exe");
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

    public void uploadNAlerts(int n, ApiService apiService) throws IOException, InterruptedException {
        switch (n) {
            case 1 -> alerts.add(carAlert);
            case 2 -> {
                alerts.add(carAlert);
                alerts.add(boatAlert);
            }
            case 3 -> {
                alerts.add(carAlert);
                alerts.add(boatAlert);
                alerts.add(rentAlert);
            }
            case 4 -> {
                alerts.add(carAlert);
                alerts.add(boatAlert);
                alerts.add(rentAlert);
                alerts.add(saleAlert);
            }
            case 5 -> {
                alerts.add(carAlert);
                alerts.add(boatAlert);
                alerts.add(rentAlert);
                alerts.add(saleAlert);
                alerts.add(toysAlert);
            }
            case 6 -> {
                alerts.add(carAlert);
                alerts.add(boatAlert);
                alerts.add(rentAlert);
                alerts.add(saleAlert);
                alerts.add(toysAlert);
                alerts.add(electronicsAlert);
            }
        }

        request = new Request();
        request.setApiService(apiService);

        // Delete alerts beforehand
        request.sendDeleteRequestToApi();

        // Upload alerts
        request.sendFiveAlertsToWebsite(alerts);
    }

    public void uploadAlertType(String type, ApiService apiService) throws IOException, InterruptedException {
        request = new Request();
        request.setApiService(apiService);

        // Delete alerts beforehand
        request.sendDeleteRequestToApi();

        // Alert type
        switch (type) {
            case "1" -> {
                jsonString = new Gson().toJson(carAlert);
            }
            case "2" -> {
                jsonString = new Gson().toJson(boatAlert);
            }
            case "3" -> {
                jsonString = new Gson().toJson(rentAlert);
            }
            case "4" -> {
                jsonString = new Gson().toJson(saleAlert);
            }
            case "5" -> {
                jsonString = new Gson().toJson(toysAlert);
            }
            case "6" -> {
                jsonString = new Gson().toJson(electronicsAlert);
            }
        }
        // Upload specific alert
        request.sendPostRequestToApi(jsonString);
    }

    public void viewListOfAlerts() {
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginXPath)));
        driver.findElement(By.xpath(loginXPath)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userIdId)));
        WebElement element =  driver.findElement(By.id(userIdId));
        element.sendKeys(userId);
        element.sendKeys(Keys.ENTER);

        // Icons
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(iconXPath)));
        List<WebElement> iconElements = driver.findElements(By.xpath(iconXPath));

        // Headings
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(headingXPath)));
        List<WebElement> headingElements = driver.findElements(By.xpath(headingXPath));
        List<String> headingList = new ArrayList<>();
        for (WebElement headingElement : headingElements) {
            String heading = headingElement.getText();
            if (!heading.equals("")) {
                headingList.add(heading);
            }
        }

        // Descriptions
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(descriptionXPath)));
        List<WebElement> descriptionElements = driver.findElements(By.xpath(descriptionXPath));
        List<String> descriptionList = new ArrayList<>();
        for (WebElement descriptionElement : descriptionElements) {
            String description = descriptionElement.getText();
            if (!description.equals("")) {
                descriptionList.add(description);
            }
        }

        // Images
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(imageXPath)));
        List<WebElement> imageElements = driver.findElements(By.xpath(imageXPath));

        // Price
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(priceXPath)));
        List<WebElement> priceElements = driver.findElements(By.xpath(priceXPath));
        List<String> priceList = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String price = priceElement.getText().replace("Price: ", "");
            if (!price.equals("")) {
                priceList.add(price);
            }
        }

        // Link
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(linkXPath)));
        List<WebElement> linkElements = driver.findElements(By.xpath(linkXPath));

        // Alert list
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(alertsXPath)));
        List<WebElement> alertsElements = driver.findElements(By.xpath(alertsXPath));

        // Check if all lists are of size 3 ? True : False
        resultOne = iconElements.size();

        resultTwo = headingList.size();

        resultThree = descriptionList.size();

        resultFour = imageElements.size();

        resultFive = priceList.size();

        resultSix = linkElements.size();

        // Check result alert count - for the last two Scenarios
        resultAlertCount = alertsElements.size();

        // Result Icon - for the last Scenario
        resultIcon = iconElements.get(0).getAttribute("src").split("/")[4];

        // Quit driver
        driver.quit();
    }
}
