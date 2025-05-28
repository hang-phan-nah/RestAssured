package tests.userManagement;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import data.generateDataUser;
import utils.Action;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class PostUser {
    private String path_users;
    private String path_user;

    @BeforeClass
    public void Setup(){
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        path_users = ConfigReader.get("base.path.users");
        path_user = ConfigReader.get("base.path.user");
    }



    @Test
    public void PostListUserSuccess(){
        JSONArray array = Action.generateInforList();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+ Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers).body(array.toString());

        Response response = httpRequest.request(Method.POST, path_users);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");
        for (int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            Assert.assertEquals(jsonPath.getString("response["+i+"].username"), object.getString("username"));
            Assert.assertEquals(jsonPath.getString("response["+i+"].lastName"), object.getString("lastName"));
            Assert.assertEquals(jsonPath.getString("response["+i+"].firstName"), object.getString("firstName"));
            Assert.assertEquals(jsonPath.getString("response["+i+"].phone"), object.getString("phone"));
            Assert.assertEquals(jsonPath.get("response["+i+"].userStatus"), object.get("userStatus"));
            Assert.assertEquals(jsonPath.getString("response["+i+"].password"), object.getString("password"));
            Assert.assertEquals(jsonPath.getString("response["+i+"].email"), object.getString("email"));
        }
    }

    @Test
    public void PostUserSuccess(){
        JSONObject jsonObject = Action.generateInfor();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+ Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers).body(jsonObject.toString());

        Response response = httpRequest.request(Method.POST, path_user);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.get("message"), "Success");

        Assert.assertEquals(jsonPath.get("response.username"), jsonObject.get("username"));
        Assert.assertEquals(jsonPath.getString("response.lastName"), jsonObject.getString("lastName"));
        Assert.assertEquals(jsonPath.getString("response.firstName"), jsonObject.getString("firstName"));
        Assert.assertEquals(jsonPath.getString("response.phone"), jsonObject.getString("phone"));
        Assert.assertEquals(jsonPath.get("response.userStatus"), jsonObject.get("userStatus"));
        Assert.assertEquals(jsonPath.getString("response.email"), jsonObject.getString("email"));

    }
}
