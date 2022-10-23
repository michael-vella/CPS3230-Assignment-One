package org.example;

import org.example.Utils.InputProvider;
import org.example.Utils.Utils;
import org.example.Utils.WebsiteService.AlertWebsite;
import org.example.Utils.WebsiteService.MaltaParkWebsite;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class MaltaParkScraper {
    public MaltaParkScraper(){}

    protected InputProvider inputProvider;
    protected AlertWebsite alertWebsite;
    protected MaltaParkWebsite maltaParkWebsite;
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected List<WebElement> items;
    protected List<Alert> alerts;

    protected Utils utils;

    // Initialise XPaths here
    protected String itemsOnPageXPath = "//div[contains(@class, 'ui') and contains(@class, 'items') and contains(@class, 'listings') and contains(@class, 'classifieds') and contains(@class, 'clearfix') and contains(@class, 'gridview')]//div[contains(@class, 'item')]//div[contains(@class, 'content')]//a";
    protected String headingXPath = "//div[contains(@class, 'details-heading') and contains(@class, 'clearfix') and contains(@class, 'tablet-visible')]//h1[contains(@class, 'top-title')]//span";
    protected String descriptionXPath = "//div[contains(@class, 'ui') and contains(@class, 'segment') and contains(@class, 'whitebg') and contains(@class, 'shadowbox')]//div[contains(@class, 'readmore-wrapper')]";
    protected String imageUrlXPath = "//div[contains(@class, 'slick-track')]//a[contains(@class, 'fancybox')]//img";
    protected String priceXPath = "//h1[contains(@class, 'top-price')]";

    public void setInputProvider(InputProvider inputProvider){ this.inputProvider = inputProvider; }
    public void setAlertWebsite(AlertWebsite alertWebsite){ this.alertWebsite = alertWebsite; }
    public void setMaltaParkWebsite(MaltaParkWebsite maltaParkWebsite){ this.maltaParkWebsite = maltaParkWebsite; }

    protected boolean setupDriver(){
        // Setup Chrome Driver
        System.setProperty("webdriver.chrome.driver", "E:/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        if (maltaParkWebsite == null){
            return false;
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

        driver.get("https://www.maltapark.com/");
        driver.manage().window().maximize();

        WebElement searchField = driver.findElement(By.id("search"));
        searchField.sendKeys(searchText);
        searchField.sendKeys(Keys.ENTER);

        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsOnPageXPath)));
            items = driver.findElements(By.xpath(itemsOnPageXPath));
        } catch (Exception e){
            System.out.println("Exception while scraping items from page: " + e);
            return false;
        }

        if (items.size() == 0){
            return false;
        }

        int scrapedItems = 0;
        boolean faultInItem;
        String heading;
        String description;
        String url;
        String imageUrl;
        int priceInCents;

        for (int i = 0; i < items.size(); i++){
            driver.navigate().to(items.get(i).getAttribute("href"));

            faultInItem = false;
            heading = "";
            description = "";
            url = "";
            imageUrl = "";
            priceInCents = 0;

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
        boolean requests = request.sendFiveAlertsToWebsite(alerts);

        if (!requests){
            return false;
        }

        return true;
    }

}
