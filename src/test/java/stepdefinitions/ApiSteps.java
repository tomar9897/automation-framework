package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.ApiUtils;
import static org.junit.jupiter.api.Assertions.*;

public class ApiSteps {

    Response response;

    @Given("user calls GET endpoint {string}")
    public void getEndpoint(String endpoint) {
        response = ApiUtils.getRequest(endpoint);
        System.out.println("GET Request URL: " + endpoint);
        System.out.println("Response Body:\n" + response.asPrettyString());
    }

    @When("user calls POST endpoint {string} with body")
    public void postWithBody(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        String body = dataTable.asMap(String.class, String.class).toString();
        response = ApiUtils.postRequest(endpoint, body);
        System.out.println("POST Request URL: " + endpoint);
        System.out.println("Request Body: " + body);
        System.out.println("Response Body:\n" + response.asPrettyString());
    }

    @Then("response code should be {int}")
    public void verifyResponseCOde(Integer expectedStatusCode) {
        assertEquals(expectedStatusCode.intValue(), response.getStatusCode(),
                "Status code mismatch.\nActual: " + response.getStatusCode() +
                "\nResponse Body:\n" + response.asPrettyString());
    }

    @Then("response should contain key {string}")
    public void responseContains(String key) {
        assertTrue(response.asString().contains(key),
                "Response does not contain expected key: " + key +
                "\nResponse Body:\n" + response.asPrettyString());
    }
}
