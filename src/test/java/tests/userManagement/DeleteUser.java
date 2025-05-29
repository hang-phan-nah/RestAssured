package tests.userManagement;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Action;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeleteUser {
    private String path;

    @BeforeClass
    public void setUp(){
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        path = ConfigReader.get("base.path.user");
    }

    @Test
    public void DeleteUserSuccess(){
        String userName = Action.getUserName();
        System.out.print(userName);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+ Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers).param("username", userName);
        Response response = httpRequest.request(Method.DELETE, path);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");
        Assert.assertEquals(jsonPath.get("response.username"), userName);
    }

    @Test
    public void DeleteWithUsernameIsNotFound(){
        String userName = "abc";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+Action.getTokenAuth());
        System.out.println(Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers).param("username", userName);
        Response response = httpRequest.request(Method.DELETE, path);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertEquals(jsonPath.get("message"), "Not found");
        Assert.assertEquals(jsonPath.get("errors"), "No user found with the submitted id");
    }
}
