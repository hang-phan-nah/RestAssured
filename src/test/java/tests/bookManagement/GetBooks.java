package tests.bookManagement;

import io.restassured.RestAssured;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static io.restassured.RestAssured.*;

public class GetBooks {

    @BeforeTest
    public void setUp(){
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        basePath = ConfigReader.get("base.path.books");
    }

    @Test
    public void GetAllBooks(){
        RequestSpecification httpRequest = given().header("Content-Type", "application/json");
        Response response = httpRequest.request(Method.GET);

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");
        Object json = jsonPath.get("response");
        Assert.assertTrue(json instanceof List, "JSON returned is a array");
    }
}
