package com.cassiano.elo7.codetest.mars.itest;

import com.cassiano.elo7.codetest.mars.CodetestApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CodetestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlateauITest {
    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void should_create_a_new_plateau_and_return_created_resource_at_Location_header() {
        Response response =
            given()
                .contentType(ContentType.JSON)
                .body("{" +
                    "\"xSize\": 5," +
                    "\"ySize\": 10" +
                "}")
            .when()
                .post("/plateau")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response();

        String location = response.getHeader("Location");
        assertTrue("Wrong Location: [" + location + "]", location.matches("http://localhost:" + port + "/plateau/[a-f,0-9]{32}]"));
    }

}
