package tests.userManagement;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GetUsers {
    private String userName;
    private String path_user;
    private String path_users;


    @BeforeClass
    public void setUp() {
        ConfigReader.loadFile("src/test/java/data/credentials.properties");
        userName = ConfigReader.get("correct.username");

        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        path_user = ConfigReader.get("base.path.user");
        path_users = ConfigReader.get("base.path.users");
    }

    @Test
    public void getAUser() {
        RequestSpecification httpRequest = given().header("Content-Type", "application/json").param("username", userName);
        Response response = httpRequest.request(Method.GET, path_user);

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        Integer statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");
        Assert.assertEquals(jsonPath.get("response.username"), userName);
    }

    @Test
    public void getAllUsers() {
        RequestSpecification httpRequest = given().header("Content-Type", "application/json");
        Response response = httpRequest.request(Method.GET, path_users);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");
        Assert.assertTrue(jsonPath.get("response") instanceof List, "JSON returned is a array");
    }
}
