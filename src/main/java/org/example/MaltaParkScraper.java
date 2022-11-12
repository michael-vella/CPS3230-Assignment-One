package org.example;

import org.example.Utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class MaltaParkScraper {
    public MaltaParkScraper(){}

    protected InputProvider inputProvider;
    protected MaltaParkWebsite maltaParkWebsite;
    protected WebDriverStatus webDriverStatus;
    protected ApiService apiService;
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected List<WebElement> items;
    protected List<Alert> alerts = new LinkedList<>();

    protected Utils utils;

    // Initialise XPaths here
    protected String itemsOnPageXPath = "//div[contains(@class, 'ui') and contains(@class, 'items') and contains(@class, 'listings') and contains(@class, 'classifieds') and contains(@class, 'clearfix') and contains(@class, 'gridview')]//div[contains(@class, 'item')]//div[contains(@class, 'content')]//a";
    protected String headingXPath = "//div[contains(@class, 'details-heading') and contains(@class, 'clearfix') and contains(@class, 'tablet-visible')]//h1[contains(@class, 'top-title')]//span";
    protected String descriptionXPath = "//div[contains(@class, 'ui') and contains(@class, 'segment') and contains(@class, 'whitebg') and contains(@class, 'shadowbox')]//div[contains(@class, 'readmore-wrapper')]";
    protected String imageUrlXPath = "//div[contains(@class, 'slick-track')]//a[contains(@class, 'fancybox')]//img";
    protected String priceXPath = "//h1[contains(@class, 'top-price')]";

    public void setInputProvider(InputProvider inputProvider){ this.inputProvider = inputProvider; }
    public void setMaltaParkWebsite(MaltaParkWebsite maltaParkWebsite){ this.maltaParkWebsite = maltaParkWebsite; }
    public void setWebDriverStatus(WebDriverStatus webDriverStatus){ this.webDriverStatus = webDriverStatus; }
    public void setApiService(ApiService apiService) { this.apiService = apiService; }

    protected boolean setupDriver(){
        if (webDriverStatus == null){
            return false;
        }

        // Setup Chrome Driver
        if (webDriverStatus.getDriverStatus() == WebDriverStatus.DRIVER_NOT_INITIALISED){
            return false;
        } else {
            System.setProperty("webdriver.chrome.driver", "E:/Chromedriver/chromedriver.exe");
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        return true;
    }

    public boolean scrapeFiveAlertsAndUploadToWebsite() throws IOException, InterruptedException {
        // Check that the input provider is initialised
        if (inputProvider == null){
            return false;
        }

        utils = new Utils();
        String searchText = "";

        int input = inputProvider.userInput();;

        switch (input) {
            case 1 -> searchText = "Car";
            case 2 -> searchText = "Boat";
            case 3 -> searchText = "Property to rent";
            case 4 -> searchText = "Property for sale";
            case 5 -> searchText = "Toys";
            case 6 -> searchText = "Electronics";
        }

        boolean setup = setupDriver();
        if (!setup){
            return false;
        }

        if (maltaParkWebsite == null){
            driver.quit();
            return false;
        }

        if (maltaParkWebsite.getMaltaParkWebsite() == MaltaParkWebsite.WEBSITE_UNAVAILABLE){
            driver.quit();
            return false;
        } else {
            driver.get("https://www.maltapark.com/");
            driver.manage().window().maximize();

            // Warning pop up code - Start
            // This part of code was needed because a warning pop up was created by MaltaPark
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("okbutton")));
            WebElement warningPopUp = driver.findElement(By.id("okbutton"));

            try {
                Thread.sleep(11000);
                // Could not use wait because okbutton was already visible when loading the element
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            warningPopUp.click();
            // Warning pop up code - End
        }

        WebElement searchField = driver.findElement(By.id("search"));
        searchField.sendKeys(searchText);
        searchField.sendKeys(Keys.ENTER);

        int scrapedItems = 0;

        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsOnPageXPath)));
            items = driver.findElements(By.xpath(itemsOnPageXPath));
        } catch (Exception e){
            System.out.println("Exception while scraping items from page: " + e);
            driver.quit();
            return false;
        }

        // Loop for 25 times just in case there is something wrong with an element
        // Example - Element missing price -> element is skipped
        // Example - Element missing image -> image is skipped
        for (int i = 0; i < 25; i++){
            // Retry because DOM refreshes
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsOnPageXPath)));
                items = driver.findElements(By.xpath(itemsOnPageXPath));
            } catch (Exception e){
                System.out.println("Exception while scraping items from page: " + e);
                driver.quit();
                return false;
            }

            if (items.size() == 0){
                driver.quit();
                return false;
            }

            driver.navigate().to(items.get(i).getAttribute("href"));

            boolean faultInItem = false;
            String heading = "";
            String description = "";
            String url = "";
            String imageUrl = "";
            int priceInCents = 0;

            // Heading
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headingXPath)));
                heading = driver.findElement(By.xpath(headingXPath)).getText();
            } catch (Exception e){
                faultInItem = true;
            }

            // Description
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(descriptionXPath)));
                description = driver.findElement(By.xpath(descriptionXPath)).getText();
            } catch (Exception e){
                faultInItem = true;
            }

            // Url
            url = driver.getCurrentUrl();

            // ImageUrl
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(imageUrlXPath)));
                imageUrl = driver.findElement(By.xpath(imageUrlXPath)).getAttribute("src");
            } catch (Exception e){
                faultInItem = true;
            }

            // PriceInCents
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(priceXPath)));
                priceInCents = utils.parsePrice(driver.findElement(By.xpath(priceXPath)).getText());
            } catch (Exception e){
                faultInItem = true;
            }

            // Check if there was something wrong with one of the items scraped - missing price, missing image, etcetera
            if (!faultInItem){
                // Add scraped item count
                scrapedItems++;

                // Add alert
                alerts.add(new Alert(input, heading, description, url, imageUrl, priceInCents));
            }

            // Break out of the loop after 5 scraped items
            if (scrapedItems == 5){
                break;
            }

            driver.navigate().back();
        }

        Request request = new Request();

        if (apiService == null){
            driver.quit();
            return false;
        }

        // Set apiService for the request class
        request.setApiService(apiService);

        boolean requests = request.sendFiveAlertsToWebsite(alerts);

        if (!requests){
            driver.quit();
            return false;
        }

        driver.quit();
        return true;
    }

}
