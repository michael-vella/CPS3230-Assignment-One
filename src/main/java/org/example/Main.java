package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Testing request...");

        Alert alert = new Alert();
        /*alert.setAlertType(6);
        alert.setHeading("Jumper Windows 11 Laptop");
        alert.setDescription("Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD");
        alert.setUrl("https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth");
        alert.setImageUrl("https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg");
        alert.setPostedBy("c55bc56a-232c-46a4-9778-7f0d41690aa2");
        alert.setPriceInCents(1000);*/

        alert.setAlertType(1);
        alert.setHeading("Mazda Car");
        alert.setDescription("Blue mazda car just for a test");
        alert.setUrl("https://www.maltapark.com/item/details/9461542");
        alert.setImageUrl("https://www.maltapark.com/asset/itemphotos/9461542/9461542_2.jpg");
        alert.setPostedBy("c55bc56a-232c-46a4-9778-7f0d41690aa2");
        alert.setPriceInCents(100000000);

        // Converting Alert Class to JsonObject
        String jsonObject = new Gson().toJson(alert);
        // System.out.println(jsonObject);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        System.out.println("Request successful");
    }
}