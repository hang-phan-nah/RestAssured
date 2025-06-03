package tests.categoryManagement;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Action;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GetCategory {

    private String path_category;
    private String path_categorys;

    @BeforeTest
    public void setUp(){
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        RestAssured.baseURI =  ConfigReader.get("base.uri");
        path_categorys = ConfigReader.get("base.path.categorys");
        path_category = ConfigReader.get("base.path.category");

    }

    @Test
    public void GetAllCategory(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers);
        Response response = httpRequest.request(Method.GET, path_categorys);

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<Map<String, Object>> jsonList = response.jsonPath().getList("response");
        for (Map<String, Object> item : jsonList){
            System.out.println(item.get("name"));
        }
        Assert.assertTrue(response.jsonPath().getList("response") instanceof List, "Node Response is a List" );
    }

    @Test
    public void GetCategoryById(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers);
        Response response = httpRequest.request(Method.GET, path_category+"/"+Action.getCategoryId());

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.getBody());
        Assert.assertEquals(jsonPath.get("message"), "Success");

    }
}
