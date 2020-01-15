package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;
    public RequestSpecification requestSpecification() throws IOException
    {
        if(req == null) {
            PrintStream logObj = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(logObj))
                    .addFilter(ResponseLoggingFilter.logResponseTo(logObj))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        else
            return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\mkhalid\\IdeaProjects\\cucumbarproject\\src\\test\\java\\files\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);

    }
    public String getJsonPath(Response response, String key)
    {
        String  respstr = response.asString();
        //String[] finalResp = respstr.split("</b><br />");
        JsonPath js = new JsonPath(respstr);
        return js.get(key).toString();
    }

}
