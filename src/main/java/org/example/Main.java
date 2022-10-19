package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialise web driver and scanner
        WebDriver driver;
        Scanner scn = new Scanner(System.in);

        /*
        System.out.println("Testing request...");

        Alert alert = new Alert();
        alert.setAlertType(6);
        alert.setHeading("Jumper Windows 11 Laptop");
        alert.setDescription("Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
        alert.setUrl("https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
        alert.setImageUrl("https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
        alert.setPostedBy("c55bc56a-232c-46a4-9778-7f0d41690aa2");
        alert.setPriceInCents(1000);

        // Converting Alert Class to JsonObject
        String jsonString = new Gson().toJson(alert);
        // System.out.println(jsonString);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println("Request successful");
        */

        String searchText = "";
        boolean loopChoice = true;
        int choice = 0;

        while (loopChoice){
            loopChoice = false;
            System.out.println("1. Search by car");
            System.out.println("2. Search by boat");
            System.out.println("3. Search by property - rent");
            System.out.println("4. Search by property - sale");
            System.out.println("5. Search by toys");
            System.out.println("6. Search by electronics");

            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scn.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Error: Input should be a number not a string.");
                choice = 0;
            }

            switch (choice) {
                case 1 -> searchText = "Car";
                case 2 -> searchText = "Boat";
                case 3 -> searchText = "Property to rent";
                case 4 -> searchText = "Property for sale";
                case 5 -> searchText = "Toys";
                case 6 -> searchText = "Electronics";
                default -> {
                    System.out.println("Incorrect choice... try again\n");
                    loopChoice = true;
                }
            }


        }

        System.out.println("Testing web driver...");
        System.setProperty("webdriver.chrome.driver", "C:/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();

        // Initialise driver
        driver.get("https://www.maltapark.com/");

        // Text field
        WebElement searchField = driver.findElement(By.id("search"));
        WebElement searchButton = driver.findElement(By.xpath("//"));

        Thread.sleep(10000);

        searchField.sendKeys(searchText);
        searchButton.click();

    }
}