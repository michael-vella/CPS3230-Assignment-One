package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils.ApiService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

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
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) {
        alertWebsiteScraper.uploadNAlerts(arg0);
    }

    @Given("I remove all alerts beforehand")
    public void iRemoveAllAlertsBeforehand() {
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        request.setApiService(apiService);
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {

    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) {

    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) {
    }

    @Given("I am an administrator of the website and I upload an alert of type <alert-type>")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfTypeAlertType() {
    }

    @And("the icon displayed should be <icon file name>")
    public void theIconDisplayedShouldBeIconFileName() {
    }
}
