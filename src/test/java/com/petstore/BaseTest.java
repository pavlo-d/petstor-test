package com.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.petstore.model.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaseTest {

    @Test
    public void createNewUser() throws URISyntaxException, IOException, InterruptedException {
        User user = new User(111,"uName", "lName", "fName","ss@ss.sss",
                "111","789789",1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(user);

        URI exampleUri = new URI("https://petstore.swagger.io/v2/user/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "User not created");
    }
    @Test
    public void loginUser() throws URISyntaxException, IOException, InterruptedException {
        User user = new User(111,"uName", "lName", "fName","ss@ss.sss",
                "111","789789",1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(user);

        URI exampleUri = new URI("https://petstore.swagger.io/v2/user/login");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "User not logged");
    }

    @Test
    public void logoutUser() throws URISyntaxException, IOException, InterruptedException {
        User user = new User(111,"uName", "lName", "fName","ss@ss.sss",
                "111","789789",1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(user);

        URI exampleUri = new URI("https://petstore.swagger.io/v2/user/logout");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "User not logged out");
    }
}
