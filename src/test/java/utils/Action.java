package utils;

import data.generateData;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Action {
    private static String token;
    private static String userName;

    public static String getTokenAuth(){
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        String path = ConfigReader.get("base.path");

        ConfigReader.loadFile("src/test/java/data/credentials.properties");
        String correctUserName = ConfigReader.get("correct.username");
        String correctPassword = ConfigReader.get("correct.password");
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", correctUserName, correctPassword);
        if (token == null){
            Response response = given().header("Content-Type", "application/json").body(requestBody).post(baseURI+path);
            token = response.jsonPath().getString("token");
        }
        //System.out.print(token);
        return token;
    }

    public void resetToken(){
        token = null;
    }

    public static String getUserName(){
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        baseURI = ConfigReader.get("base.uri");
        String path = ConfigReader.get("base.path.user");

        JSONObject jsonObject = Action.generateInfor();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer "+ Action.getTokenAuth());

        Response response = given().headers(headers).body(jsonObject.toString()).post(baseURI+path);
        userName = response.jsonPath().getString("response.username");

        return userName;
    }

    public static JSONArray generateInforList(){
        JSONObject userData_1 = new JSONObject();
        userData_1.put("username", generateData.generateUserName());
        userData_1.put("firstName", generateData.generateLastName());
        userData_1.put("lastName", generateData.generateFirstName());
        userData_1.put("email", generateData.generateEmail());
        userData_1.put("password", generateData.generatePassword());
        userData_1.put("phone", generateData.generatePhoneNumber());
        userData_1.put("userStatus", 1);

        JSONObject userData_2 = new JSONObject();
        userData_2.put("username", generateData.generateUserName());
        userData_2.put("firstName", generateData.generateLastName());
        userData_2.put("lastName", generateData.generateFirstName());
        userData_2.put("email", generateData.generateEmail());
        userData_2.put("password", generateData.generatePassword());
        userData_2.put("phone", generateData.generatePhoneNumber());
        userData_2.put("userStatus", 1);

        JSONArray array = new JSONArray();
        array.put(userData_1);
        array.put(userData_2);
        return array;
    }

    public static JSONObject generateInfor(){
        JSONObject userData_1 = new JSONObject();
        userData_1.put("username", generateData.generateUserName());
        userData_1.put("firstName", generateData.generateLastName());
        userData_1.put("lastName", generateData.generateFirstName());
        userData_1.put("email", generateData.generateEmail());
        userData_1.put("password", generateData.generatePassword());
        userData_1.put("phone", generateData.generatePhoneNumber());
        userData_1.put("userStatus", 1);

        return userData_1;
    }

    public static int getCategoryId() {
        ConfigReader.loadFile("src/test/java/resources/config.properties");
        RestAssured.baseURI = ConfigReader.get("base.uri");
        RestAssured.basePath = ConfigReader.get("base.path.categorys");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", Action.getTokenAuth());
        RequestSpecification httpRequest = given().headers(headers);
        Response response = httpRequest.request(Method.GET);
        List<Map<String, Object>> jsonList = response.jsonPath().getList("response");
        int randomId = 0;
        if (jsonList != null && !jsonList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(jsonList.size());
            randomId = (int) jsonList.get(randomIndex).get("id");
            //System.out.println(randomId);
        }
        return randomId;

    }
}
