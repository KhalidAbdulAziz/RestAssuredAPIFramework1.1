
//@RunWith(Cucumber.class)
package StepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.log4j.Logger;
import pojo.Location;
import pojo.SerializeApi;
import resources.APIResources;
import resources.TestData;
import utils.Utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class StepDefination extends Utils {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    RequestSpecification req;
    JsonPath js;
    String place_id;


    TestData data = new TestData();

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_Place_Payload_with(String name, String language, String address) throws IOException {

        res = given().spec((requestSpecification())).body(data.addPlacePayLoad(name,language,address));

    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method){

       APIResources  resourceObj = APIResources.valueOf(resource);
        System.out.println( resourceObj.getResource());

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if(method.equalsIgnoreCase("POST"))

           response = res.when().post( resourceObj.getResource());
              //  then().spec(resspec).extract().response()
    else if (method.equalsIgnoreCase("GET"))

        response = res.when().get( resourceObj.getResource());
    }

    @Then("the API call got success with status code {int}")
    public void the_API_call_got_success_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
       assertEquals(response.getStatusCode(),200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String strKey, String strValue) {
        // Write code here that turns the phrase above into concrete actions
        String  respstr = response.asString();
        String[] finalResp = respstr.split("</b><br />");
        js = new JsonPath(finalResp[1]);
        String test = js.getString(strKey);
        place_id = js.getString("place_id");
        //System.out.println(test);
        assertEquals(js.get(strKey).toString(),strValue);

    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
//        String placeID = getJsonPath(response,"place_id");
        res = given().spec((requestSpecification()).queryParam("place_id",place_id));
        user_calls_with_http_request(resource,"GET");
        String actualName = getJsonPath(response,"name");
        assertEquals(actualName,expectedName);

    }
}