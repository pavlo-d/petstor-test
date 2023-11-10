package com.petstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.petstore.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseTest {

    @Test
    public void createNewUser() throws URISyntaxException, IOException, InterruptedException {
        User user = new User(111, "uName", "lName", "fName", "ss@ss.sss",
                "111", "789789", 1);
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
    public void createListOfNewUser() throws URISyntaxException, IOException, InterruptedException {
        User userFirst = new User(1111, "uName1", "lName1", "fName1", "ss@ss.sss",
                "1111", "789789", 1);
        User userSecond = new User(2222, "uName2", "lName2", "fName2", "ss@ss.sss",
                "2222", "789789", 2);
        List<User> users = new ArrayList<>();
        users.add(userFirst);
        users.add(userSecond);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(users);

        URI exampleUri = new URI("https://petstore.swagger.io/v2/user/createWithList");
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
        User user = new User(111, "uName", "lName", "fName", "ss@ss.sss",
                "111", "789789", 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(user);
        String url = String.format("https://petstore.swagger.io/v2/user/login?username=%s&password=%s",
                user.getUsername(), user.getPassword());
        URI exampleUri = new URI(url);

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
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
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

    @Test
    public void createNewPet() throws URISyntaxException, IOException, InterruptedException {
        List<String> url = new ArrayList<>(Arrays.asList("xyz", "abc"));
        List<Tags> tags = new ArrayList<>(Arrays.asList(new Tags(111, "newTage")));
        Pet pet = new Pet(333, new Category(777, "newCategory"), "cat", url,
                tags, Status.available);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(pet);
        URI exampleUri = new URI("https://petstore.swagger.io/v2/pet/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "Pet not created");
    }

    @Test
    public void updatePetNameAndStatus() throws URISyntaxException, IOException, InterruptedException {
        List<String> url = new ArrayList<>(Arrays.asList("xyz", "abc"));
        List<Tags> tags = new ArrayList<>(Arrays.asList(new Tags(111, "newTage")));

        Pet pet = new Pet(333, new Category(777, "newCategory"), "cat", url,
                tags, Status.available);

        pet.setName("BigCat");
        pet.setStatus(Status.sold);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(pet);
        URI exampleUri = new URI("https://petstore.swagger.io/v2/pet/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "Pet Name and Status not updated");
    }

    @Test
    public void deletePet() throws URISyntaxException, IOException, InterruptedException {
        List<String> url = new ArrayList<>(Arrays.asList("xyz", "abc"));
        List<Tags> tags = new ArrayList<>(Arrays.asList(new Tags(111, "newTage")));

        Pet pet = new Pet(333, new Category(777, "newCategory"), "cat", url,
                tags, Status.available);

        String urlDelete = String.format("https://petstore.swagger.io/v2/pet/%s", pet.getId());
        URI exampleUri = new URI(urlDelete);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(exampleUri)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        Assert.assertEquals(response.statusCode(), 200, "Pet not deleted");
    }
}
