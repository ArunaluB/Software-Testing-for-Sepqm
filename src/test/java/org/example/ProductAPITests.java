package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert; //Testng class3s
import org.testng.annotations.Test; //Testng classes

public class ProductAPITests {

    String baseUrl = "https://automationexercise.com/api/productsList";

    @Test
    public void testGetAllProducts_Valid() {
        Response response = RestAssured.get(baseUrl);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("products"));
    }

    @Test
    public void testInvalidEndpoint() {
        Response response = RestAssured.get("https://automationexercise.com/api/invalidProducts");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 404);
    }


    @Test
    public void testUnsupportedMethod() {
        Response response = RestAssured.post(baseUrl);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testResponseTime() {
        Response response = RestAssured.get(baseUrl);
        Assert.assertTrue(response.getTime() < 2000);
    }

    @Test
    public void testJsonStructure() {
        Response response = RestAssured.get(baseUrl);
        Assert.assertTrue(response.jsonPath().getList("products") != null);
    }

    @Test
    public void testUnsupportedMethodWithJsonValidation() {
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .post(baseUrl);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 405);
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), 405);
        Assert.assertEquals(response.jsonPath().getString("message"), "This request method is not supported.");
    }

    @Test
    public void testMethodSupportComparison() {
        Response getResponse = RestAssured.get(baseUrl);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertTrue(getResponse.getBody().asString().contains("products"));

        Response postResponse = RestAssured.post(baseUrl);
        Assert.assertEquals(postResponse.getStatusCode(), 200); //change to 405 to change
    }

    @Test
    public void testSearchProductWithoutParameter() {
        String searchUrl = "https://automationexercise.com/api/searchProduct";

        Response response = RestAssured.post(searchUrl); // No headers or body

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Fix the assertions to match actual response
        Assert.assertEquals(response.getStatusCode(), 200); // HTTP is still 200 even for logical error
        Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400);
        Assert.assertEquals(response.jsonPath().getString("message"),
                "Bad request, search_product parameter is missing in POST request.");
    }
}
