package com.cassiano.elo7.codetest.mars.itest;

import com.cassiano.elo7.codetest.mars.CodetestApplication;
import com.cassiano.elo7.codetest.mars.integration.PlateauRepository;
import com.cassiano.elo7.codetest.mars.integration.ProbeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CodetestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProbeITest {
    @LocalServerPort
    private int port;

    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    private PlateauRepository plateauRepository;
    private String plateauLocation;


    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        plateauLocation = createPlateau();
    }

    @After
    public void tearDown() {
        probeRepository.deleteAll();
        plateauRepository.deleteAll();
    }


    @Test
    public void should_create_a_new_probe_at_created_plateau_and_return_created_resource_at_Location_header() {
        Response response =
            given()
                .contentType(ContentType.JSON)
                .body("{" +
                    "\"direction\": \"N\"," +
                    "\"positionX\": 1," +
                    "\"positionY\": 2" +
                "}")
            .when()
                .post(plateauLocation + "/probe")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().response();

        String location = response.getHeader("Location");
        assertTrue("Wrong Location: [" + location + "]", location.matches(plateauLocation + "/probe/[a-f,0-9]{32}"));
    }


    @Test
    public void should_find_created_probe_with_Location_URI() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{" +
                                "\"direction\": \"N\"," +
                                "\"positionX\": 3," +
                                "\"positionY\": 0" +
                                "}")
                        .when()
                        .post(plateauLocation + "/probe")
                        .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().response();

        String location = response.getHeader("Location");


        Response getResponse =
                when()
                    .get(location)
                .then()
                        .statusCode(HttpStatus.SC_OK)
                    .contentType(ContentType.JSON)
                    .extract().response();

        JsonPath jsonPath = getResponse.jsonPath();
        assertTrue(jsonPath.getString("id").matches("[a-f,0-9]{32}"));
        assertEquals("N", jsonPath.getString("direction"));
        assertEquals(3, jsonPath.getInt("positionX"));
        assertEquals(0, jsonPath.getInt("positionY"));
    }


    @Test
    public void should_find_all_created_probes() {
        given()
            .contentType(ContentType.JSON)
                .body("{" +
                        "\"direction\": \"N\"," +
                        "\"positionX\": 3," +
                        "\"positionY\": 0" +
                        "}")
                .when()
                .post(plateauLocation + "/probe")
        .then()
            .statusCode(HttpStatus.SC_CREATED);
        given()
            .contentType(ContentType.JSON)
                .body("{" +
                        "\"direction\": \"S\"," +
                        "\"positionX\": 4," +
                        "\"positionY\": 2" +
                        "}")
                .when()
                .post(plateauLocation + "/probe")
        .then()
            .statusCode(HttpStatus.SC_CREATED);



        when()
            .get(plateauLocation + "/probe")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("direction", hasItems("N", "S"))
            .body("positionX", hasItems(3, 4))
            .body("positionY", hasItems(0, 2));
    }


    @Test
    public void should_find_move_probe_and_return_last_position() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{" +
                                "\"direction\": \"N\"," +
                                "\"positionX\": 3," +
                                "\"positionY\": 0" +
                                "}")
                        .when()
                        .post(plateauLocation + "/probe")
                        .then()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract().response();

        String location = response.getHeader("Location");


        Response getResponse =
                given()
                    .contentType(ContentType.JSON)
                    .body("[ \"R\", \"M\", \"M\", \"L\", \"M\", \"R\", \"M\"  ]")
                .when()
                    .post(location + "/move")
                        .then()
                    .statusCode(HttpStatus.SC_OK)
                    .contentType(ContentType.JSON)
                    .extract().response();

        JsonPath jsonPath = getResponse.jsonPath();
        assertTrue(jsonPath.getString("id").matches("[a-f,0-9]{32}"));
        assertEquals("E", jsonPath.getString("direction"));
        assertEquals(6, jsonPath.getInt("positionX"));
        assertEquals(1, jsonPath.getInt("positionY"));
    }

// TODO: Test Errors

    private String createPlateau() {
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

        return response.getHeader("Location");
    }
}
