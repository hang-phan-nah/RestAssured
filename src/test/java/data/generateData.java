package data;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class generateData {

    public static String generateUserName(){
        return "User_" + UUID.randomUUID().toString().substring(0, 5);
    }
    public static String generateEmail(){
        return generateUserName() + "@example.com";
    }
    public static String generateLastName(){
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    public static String generateFirstName(){
        Faker fake = new Faker();
        return fake.name().firstName();
    }

    public static String generatePassword(){
        Faker faker = new Faker();
        return faker.internet().password(8, 12, true, true, true);
    }

    public static String generatePhoneNumber() {
        return "09" + (int)(Math.random() * 100000000);
    }

    public static String generateString(){
        Faker faker = new Faker();
        return faker.lorem().word();
    }

    public static String generateDate(){
        Faker faker = new Faker();
        Date date = faker.date().past(400, TimeUnit.DAYS);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

}
