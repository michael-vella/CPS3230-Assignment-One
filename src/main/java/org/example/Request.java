package org.example;

import com.google.gson.Gson;
import org.example.Utils.ApiService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Request {

    public Request(){}
    protected ApiService apiService;

    public void setApiService(ApiService apiService){ this.apiService = apiService; }

    public int sendPostRequestToApi(String jsonString) throws IOException, InterruptedException {
        if (apiService == null) {
            return -1;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        // 201 Created
        return response.statusCode();
    }

    public int sendDeleteRequestToApi() throws IOException, InterruptedException {
        if (apiService == null) {
            return -1;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert?userId=c55bc56a-232c-46a4-9778-7f0d41690aa2"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        // 200 OK
        return response.statusCode();
    }

    public boolean sendFiveAlertsToWebsite(List<Alert> alertList) throws IOException, InterruptedException {
        for (int i = 0; i < alertList.size(); i++){
            // Converting Alert Class to JsonObject
            String jsonString = new Gson().toJson(alertList.get(i));

            if (sendPostRequestToApi(jsonString) != 201){
                return false;
            }
        }
        return true;
    }
}
