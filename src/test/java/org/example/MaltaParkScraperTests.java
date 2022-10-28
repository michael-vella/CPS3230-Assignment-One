package org.example;

import org.example.Utils.ApiService;
import org.example.Utils.InputProvider;
import org.example.Utils.MaltaParkWebsite;
import org.example.Utils.WebDriverStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

public class MaltaParkScraperTests {
    MaltaParkScraper maltaParkScraper;
    InputProvider inputProvider;
    MaltaParkWebsite maltaParkWebsite;
    WebDriverStatus webDriverStatus;
    ApiService apiService;
    Request request;

    @BeforeEach
    public void setup(){
        maltaParkScraper = new MaltaParkScraper();
        request = new Request();
    }

    @AfterEach
    public void teardown(){
        maltaParkScraper = null;
    }

    @Test
    public void testSetupDriverWhenThereIsNoDriver(){
        // Exercise
        boolean result = maltaParkScraper.setupDriver();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testSetupDriverWhenThereIsNoDriverInitialised(){
        // Setup
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_NOT_INITIALISED);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);

        // Exercise
        boolean result =  maltaParkScraper.setupDriver();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testSetupDriverWithCorrectInitialisation(){
        // Setup
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);

        // Exercise
        boolean result = maltaParkScraper.setupDriver();

        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testScraperWithNotInitialisedInputProvider() throws IOException, InterruptedException {
        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testScraperWithDriverNotInitialised() throws IOException, InterruptedException {
        // Setup
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        inputProvider = Mockito.mock(InputProvider.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_NOT_INITIALISED);
        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.CAR);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setInputProvider(inputProvider);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testScraperForCarWithMaltaParkWebsiteNotInitialised() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.CAR);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setInputProvider(inputProvider);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testScraperForCarWithMaltaParkWebsiteNotAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.CAR);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_UNAVAILABLE);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testScraperForCarWithMaltaParkWebsiteWithNoApiService() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.CAR);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testScraperForCarWithMaltaParkWebsiteWithApiServiceNotInitialised() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.CAR);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.NOT_INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    public void testScraperForCarWithMaltaParkWebsiteAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.CAR);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testScraperForBoatWithMaltaParkWebsiteAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.BOAT);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testScraperForPropertyForRentWithMaltaParkWebsiteAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.PROPERTY_FOR_RENT);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testScraperForPropertyForSaleWithMaltaParkWebsiteAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.PROPERTY_FOR_SALE);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testScraperForToysWithMaltaParkWebsiteAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.TOYS);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void testScraperForElectronicsWithMaltaParkWebsiteAvailable() throws IOException, InterruptedException {
        // Setup
        inputProvider = Mockito.mock(InputProvider.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        maltaParkWebsite = Mockito.mock(MaltaParkWebsite.class);
        apiService = Mockito.mock(ApiService.class);

        Mockito.when(inputProvider.userInput()).thenReturn(InputProvider.ELECTRONICS);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(maltaParkWebsite.getMaltaParkWebsite()).thenReturn(MaltaParkWebsite.WEBSITE_AVAILABLE);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);

        maltaParkScraper.setInputProvider(inputProvider);
        maltaParkScraper.setWebDriverStatus(webDriverStatus);
        maltaParkScraper.setMaltaParkWebsite(maltaParkWebsite);
        maltaParkScraper.setApiService(apiService);

        // Exercise
        boolean result = maltaParkScraper.scrapeFiveAlertsAndUploadToWebsite();

        // Verify
        Assertions.assertTrue(result);
    }
}
