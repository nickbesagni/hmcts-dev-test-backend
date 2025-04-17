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
class GetTaskFunctionalTest {

    @Value("${TEST_URL:http://localhost:8080}")
    private String testUrl;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = testUrl;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    void getTask() {
        // First, create a task
        String requestBody = "{\"title\":\"Title\",\"description\":\"Description\",\"status\":\"Pending\",\"dueDateTime\":\"2025-04-16T22:00:00\"}";

        Response createResponse = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/tasks")
            .then()
            .extract().response();

        Long taskId = createResponse.jsonPath().getLong("id");

        // Then, get the task by ID
        Response response = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/tasks/" + taskId)
            .then()
            .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.asString().contains("Title"));
    }
}
