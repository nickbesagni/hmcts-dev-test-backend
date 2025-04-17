package uk.gov.hmcts.reform.dev;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ApplicationSmokeTest {

    @Value("${TEST_URL:http://localhost:8080}")
    private String testUrl;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = testUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    void smokeTest() {
        // Verify that the application is running and the root endpoint returns the expected response
        Response response = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/")
            .then()
            .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.asString().startsWith("Welcome"));

        // Verify that the /tasks endpoint is accessible
        Response tasksResponse = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/tasks")
            .then()
            .extract().response();

        Assertions.assertEquals(200, tasksResponse.statusCode());
    }
}
