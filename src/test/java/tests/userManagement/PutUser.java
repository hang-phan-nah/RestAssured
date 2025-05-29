package tests.userManagement;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import utils.Action;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PutUser {
    private String path_user;

    @BeforeTest
    public void setUp() {
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        path_user = ConfigReader.get("base.path.user");
        RestAssured.baseURI = ConfigReader.get("base.uri");
    }

    @Test
    public void PutUserSuccess() {
        JSONObject requestBody = Action.generateInfor();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers).body(requestBody.toString());
        Response response = httpRequest.request(Method.PUT, path_user + "/122");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");

        Assert.assertEquals(jsonPath.get("response.username"), requestBody.get("username"));
        Assert.assertEquals(jsonPath.getString("response.lastName"), requestBody.getString("lastName"));
        Assert.assertEquals(jsonPath.getString("response.firstName"), requestBody.getString("firstName"));
        Assert.assertEquals(jsonPath.getString("response.phone"), requestBody.getString("phone"));
        Assert.assertEquals(jsonPath.get("response.userStatus"), requestBody.get("userStatus"));
        Assert.assertEquals(jsonPath.getString("response.email"), requestBody.getString("email"));
    }

    @Test
    public void PutUserWithNotFoundId() {
        JSONObject requestBody = Action.generateInfor();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers).body(requestBody.toString());
        Response response = httpRequest.request(Method.PUT, path_user + "/22222222222");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(jsonPath.get("message"), "Not found");
        Assert.assertEquals(jsonPath.get("errors"), "No user found with the submitted id");
    }
}
