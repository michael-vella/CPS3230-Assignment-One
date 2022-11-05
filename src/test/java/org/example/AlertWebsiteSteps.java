package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils.ApiService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.IOException;

public class AlertWebsiteSteps {
    AlertWebsiteScraper alertWebsiteScraper;
    ApiService apiService;
    Request request;

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        alertWebsiteScraper = new AlertWebsiteScraper();
        alertWebsiteScraper.setupDriver();
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        alertWebsiteScraper.validLogin();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        Assertions.assertTrue(alertWebsiteScraper.result);
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        alertWebsiteScraper.invalidLogin();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        Assertions.assertTrue(alertWebsiteScraper.result);
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) throws IOException, InterruptedException {
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        alertWebsiteScraper.uploadNAlerts(arg0, apiService);
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        alertWebsiteScraper.viewListOfAlerts();
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        Assertions.assertEquals(3, alertWebsiteScraper.resultOne);
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        Assertions.assertEquals(3, alertWebsiteScraper.resultTwo);
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        Assertions.assertEquals(3, alertWebsiteScraper.resultThree);
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        Assertions.assertEquals(3, alertWebsiteScraper.resultFour);
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        Assertions.assertEquals(3, alertWebsiteScraper.resultFive);
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        Assertions.assertEquals(3, alertWebsiteScraper.resultSix);
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) {
        Assertions.assertEquals(arg0, alertWebsiteScraper.resultAlertCount);
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) throws IOException, InterruptedException {
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        alertWebsiteScraper.uploadNAlerts(arg0 + 1, apiService);
    }

    @Given("I am an administrator of the website and I upload an alert of type {string}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType(String arg0) throws IOException, InterruptedException {
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        alertWebsiteScraper.uploadAlertType(arg0, apiService);
    }

    @And("the icon displayed should be {string}")
    public void theIconDisplayedShouldBe(String arg0) {
        Assertions.assertEquals(arg0, alertWebsiteScraper.resultIcon);
    }
}
