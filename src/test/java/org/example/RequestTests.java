package org.example;

import com.google.gson.Gson;
import org.example.Utils.ApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RequestTests {
    Request request;
    Alert alert;
    String jsonString;
    ApiService apiService;
    List<Alert> alertList = new LinkedList<>();

    @BeforeEach
    public void setup() {
        request = new Request();
        // Mock alert
        alert = new Alert(
                1,
                "VW Polo 1.2 2002",
                "Just a test description",
                "https://www.maltapark.com/item/details/9511396",
                "https://www.maltapark.com/asset/itemphotos/9511396/9511396_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=2",
                120000
        );
        jsonString = new Gson().toJson(alert);
    }

    @AfterEach
    public void teardown(){
        request = null;
        alert = null;
        jsonString = null;
    }

    @Test
    public void testPostRequestWhenThereIsNoApiService() throws IOException, InterruptedException {
        // Exercise
        int result = request.sendPostRequestToApi(jsonString);

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testPostRequestWithApiServiceNotInitialised() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.NOT_INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.sendPostRequestToApi(jsonString);

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testPostRequestWithCorrectJsonBody() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.sendPostRequestToApi(jsonString);

        // Verify
        Assertions.assertEquals(201, result);
    }

    // Test should have failed - Bug in Alert website
    @Test
    public void testPostRequestWithInitialisedApiServiceAndWrongAlertType() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        request.setApiService(apiService);
        alert.setAlertType(10); // Alert Type 10 does not exist
        jsonString = new Gson().toJson(alert);

        // Exercise
        int result = request.sendPostRequestToApi(jsonString);

        // Verify
        Assertions.assertEquals(201, result);
    }

    @Test
    public void testPostRequestWithInitialisedApiServiceAndWrongJsonBody() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        request.setApiService(apiService);
        jsonString = "{ \"wrongBody\": true }";

        // Exercise
        int result = request.sendPostRequestToApi(jsonString);

        // Verify
        Assertions.assertEquals(400, result);
    }

    @Test
    public void testDeleteRequestWhenThereIsNoApiService() throws IOException, InterruptedException {
        // Exercise
        int result = request.sendDeleteRequestToApi();

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testDeleteRequestWithApiServiceNotInitialised() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.NOT_INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.sendDeleteRequestToApi();

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testDeleteRequestWithApiServiceInitialised() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.sendDeleteRequestToApi();

        // Verify
        Assertions.assertEquals(200, result);
    }

    @Test
    public void testSendFiveAlertsToWebsiteWithAllCorrectJsonBody() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(ApiService.class);
        Mockito.when(apiService.getApi()).thenReturn(ApiService.INITIALISED);
        request.setApiService(apiService);

        for (int i = 0; i < 5; i++){
            alertList.add(alert);
        }

        // Exercise
        boolean result = request.sendFiveAlertsToWebsite(alertList);
        Collection<Invocation> invocations = Mockito.mockingDetails(apiService).getInvocations();

        // Verify
        Assertions.assertTrue(result);
        Assertions.assertEquals(5, invocations.size());
    }
}
