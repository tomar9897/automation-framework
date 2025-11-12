package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiUtils {

    static {
        RestAssured.baseURI = constants.ApiEndpoints.BASE_URL;
    }

    // Request spec - reusable common settings
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .build();

    // Response sPecification - reusable expected validations
    private static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectResponseTime(lessThan(3000L))
            .build();

    public static Response getRequest(String endpoint) {
        return given()
                .spec(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    // GET with query params
    public static Response getRequestWithQuery(String endpoint, String paramKey, String paramValue) {
        return given()
                .spec(requestSpec)
                .queryParam(paramKey, paramValue)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    // GET with path params
    public static Response getRequestWithPath(String endpoint, String paramName, String paramValue) {
        return given()
                .spec(requestSpec)
                .pathParam(paramName, paramValue)
                .when()
                .get(endpoint + "/{" + paramName + "}")
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static Response postRequest(String endpoint, String body) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    // POST with Auth Token
    public static Response postRequestWithAuth(String endpoint, String token, String body) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static Response putRequest(String endpoint, String body) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static Response patchRequest(String endpoint, String body) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .patch(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static Response deleteRequest(String endpoint) {
        return given()
                .spec(requestSpec)
                .when()
                .delete(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static Response getWithBasicAuth(String endpoint, String username, String password) {
        return given()
                .spec(requestSpec)
                .auth().basic(username, password)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static Response getWithOAuth2(String endpoint, String token) {
        return given()
                .spec(requestSpec)
                .auth().oauth2(token)
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    public static void validateStatusCode(Response response, int expectedStatus) {
        response.then().statusCode(expectedStatus);
    }

    public static void validateFieldValue(Response response, String jsonPath, String expectedValue) {
        response.then().body(jsonPath, equalTo(expectedValue));
    }

    public static void validateFieldIsNotNull(Response response, String jsonPath) {
        response.then().body(jsonPath, notNullValue());
    }

    public static Response getWithLogs(String endpoint) {
        return given()
                .spec(requestSpec)
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().body()
                .spec(responseSpec)
                .extract().response();
    }
}
