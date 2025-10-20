package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    static {
        RestAssured.baseURI = constants.ApiEndpoints.BASE_URL;
    }

    public static Response getRequest(String endpoint) {
        return given()
                .relaxedHTTPSValidation()
                .when()
                .get(RestAssured.baseURI + endpoint)
                .then()
                .extract().response();
    }

    public static Response postRequest(String endpoint, String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(RestAssured.baseURI + endpoint)
                .then()
                .extract().response();
    }
}
