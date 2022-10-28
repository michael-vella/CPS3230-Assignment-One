package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class AlertWebsiteSteps {
    AlertWebsiteScraper alertWebsiteScraper;

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

    }

    @Given("I remove all alerts beforehand")
    public void iRemoveAllAlertsBeforehand() {

    }
}
