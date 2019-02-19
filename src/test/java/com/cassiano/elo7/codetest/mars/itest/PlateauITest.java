package com.cassiano.elo7.codetest.mars.itest;

import com.cassiano.elo7.codetest.mars.CodetestApplication;
import com.cassiano.elo7.codetest.mars.integration.PlateauRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CodetestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlateauITest {
    @LocalServerPort
    private int port;

    @Autowired
    private PlateauRepository plateauRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @After
    public void tearDown() {
        plateauRepository.deleteAll();
    }

    @Test
    public void should_create_a_new_plateau_and_return_created_resource_at_Location_header() {
        Response response =
            given()
                .contentType(ContentType.JSON)
                .body("{" +
                    "\"sizeX\": 5," +
                    "\"sizeY\": 10" +
                "}")
            .when()
                .post("/plateau")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response();

        String location = response.getHeader("Location");
        assertTrue("Wrong Location: [" + location + "]", location.matches("http://localhost:" + port + "/plateau/[a-f,0-9]{32}"));
    }


    @Test
    public void should_find_created_plateau_with_Location_URI() {
        Response postResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body("{" +
                                "\"sizeX\": 5," +
                                "\"sizeY\": 10" +
                                "}")
                .when()
                        .post("/plateau")
                .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().response();

        String location = postResponse.getHeader("Location");


        Response getResponse =
                when()
                    .get(location)
                .then()
                        .statusCode(HttpStatus.SC_OK)
                    .contentType(ContentType.JSON)
                    .extract().response();

        JsonPath jsonPath = getResponse.jsonPath();
        assertTrue(jsonPath.getString("id").matches("[a-f,0-9]{32}"));
        assertEquals(5, jsonPath.getInt("sizeX"));
        assertEquals(10, jsonPath.getInt("sizeY"));
    }


    @Test
    public void should_find_all_created_plateau() {
        given()
            .contentType(ContentType.JSON)
            .body("{" +
                    "\"sizeX\": 5," +
                    "\"sizeY\": 10" +
                    "}")
        .when()
            .post("/plateau")
        .then()
            .statusCode(HttpStatus.SC_CREATED);
        given()
            .contentType(ContentType.JSON)
            .body("{" +
                    "\"sizeX\": 15," +
                    "\"sizeY\": 32" +
                    "}")
        .when()
            .post("/plateau")
        .then()
            .statusCode(HttpStatus.SC_CREATED);



        when()
            .get("/plateau")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("sizeX", hasItems(15, 5))
            .body("sizeY", hasItems(10, 32));
    }

}
