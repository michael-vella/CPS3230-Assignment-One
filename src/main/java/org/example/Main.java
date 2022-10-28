package org.example;

public class Main {
    public static void main(String args[]){
        AlertWebsiteScraper alertWebsiteScraper = new AlertWebsiteScraper();

        alertWebsiteScraper.setupDriver();

        alertWebsiteScraper.validLogin();
    }
}
