package utils;

import data.generateDataUser;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        userData_1.put("username", generateDataUser.generateUserName());
        userData_1.put("firstName", generateDataUser.generateLastName());
        userData_1.put("lastName", generateDataUser.generateFirstName());
        userData_1.put("email", generateDataUser.generateEmail());
        userData_1.put("password", generateDataUser.generatePassword());
        userData_1.put("phone", generateDataUser.generatePhoneNumber());
        userData_1.put("userStatus", 1);

        JSONObject userData_2 = new JSONObject();
        userData_2.put("username", generateDataUser.generateUserName());
        userData_2.put("firstName", generateDataUser.generateLastName());
        userData_2.put("lastName", generateDataUser.generateFirstName());
        userData_2.put("email", generateDataUser.generateEmail());
        userData_2.put("password", generateDataUser.generatePassword());
        userData_2.put("phone", generateDataUser.generatePhoneNumber());
        userData_2.put("userStatus", 1);

        JSONArray array = new JSONArray();
        array.put(userData_1);
        array.put(userData_2);
        return array;
    }

    public static JSONObject generateInfor(){
        JSONObject userData_1 = new JSONObject();
        userData_1.put("username", generateDataUser.generateUserName());
        userData_1.put("firstName", generateDataUser.generateLastName());
        userData_1.put("lastName", generateDataUser.generateFirstName());
        userData_1.put("email", generateDataUser.generateEmail());
        userData_1.put("password", generateDataUser.generatePassword());
        userData_1.put("phone", generateDataUser.generatePhoneNumber());
        userData_1.put("userStatus", 1);

        return userData_1;
    }
}
