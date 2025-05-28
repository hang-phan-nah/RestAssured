package tests.authenticate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class PostLogin {
    private  String wrongUserName;
    private  String wrongPassWord;
    private  String correctUserName;
    private  String correctPassword;
    private  String path;


    @BeforeClass
    public  void setUp(){
        ConfigReader.loadFile("src/test/java/data/credentials.properties");
        wrongUserName = ConfigReader.get("wrong.username");
        wrongPassWord = ConfigReader.get("wrong.password");
        correctUserName = ConfigReader.get("correct.username");
        correctPassword = ConfigReader.get("correct.password");

        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        path = ConfigReader.get("base.path");
    }


    @Test
    public void postLoginWithWrongPassword(){
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", correctUserName, wrongPassWord);
        RequestSpecification httpRequest = given().header("Content-Type", "application/json").body(requestBody);
        Response response = httpRequest.request(Method.POST, path);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Integer status = response.getStatusCode();
        Assert.assertEquals(status, 400);
        Assert.assertEquals(jsonPath.get("message"), "Login failed");
        Assert.assertEquals(jsonPath.get("errors"), "Password is incorrect");
    }

    @Test
    public void postLoginWithWrongUserName(){
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", wrongUserName, correctPassword);
        RequestSpecification httpRequest = given().header("Content-Type", "application/json").body(requestBody);
        Response response = httpRequest.request(Method.POST, path);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Integer status = response.getStatusCode();
        Assert.assertEquals(status, 400);
        Assert.assertEquals(jsonPath.get("message"), "Login failed");
        Assert.assertEquals(jsonPath.get("errors"), "User name not found");

    }


    @Test
    public void postLoginSuccess() {
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", correctUserName, correctPassword);
        RequestSpecification httpRequest = given().header("Content-Type", "application/json").body(requestBody);
        Response response = httpRequest.request(Method.POST, path);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Integer status = response.getStatusCode();
        Assert.assertEquals(status, 200);
    }


}