package com.automation.tests;

import com.automation.config.RestAPIBase;
import com.automation.utils.CSVReader;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APITests extends RestAPIBase {

    // Test case to verify the count of airports returned by the API
    @Test(priority = 10, dataProviderClass = CSVReader.class, dataProvider = "csvDataProvider", description = "Verify Airport Count")
    public void verifyAirportCount(int expectedCount) {
        log.info("Starting test \"Verify Airport Count\"");
        try {
            Response response = RestAssured.get("/airports");
            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

            JsonPath jsonPath = response.jsonPath();
            int airportCount = jsonPath.getList("data").size();
            Allure.step("Verifying Airport Count");
            Assert.assertEquals(airportCount, expectedCount, "Expected " + expectedCount + " airports");
        } catch (Exception e) {
            // If an exception occurs, log it and fail the test
            Allure.step("Exception encountered while verifying airport count: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        log.info("Test \"Verify Airport Count\" finished");
    }

    // Test case to verify if specific airports are present in the API response
    @Test(priority = 20, dataProviderClass = CSVReader.class, dataProvider = "csvDataProvider", description = "Verify Specific Airports")
    public void verifySpecificAirports(String airport1, String airport2, String airport3) {
        log.info("Starting test \"Verify Specific Airports\"");
        try {
            Response response = RestAssured.get("/airports");
            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

            JsonPath jsonPath = response.jsonPath();
            String airportNames = jsonPath.getString("data.attributes.name");

            Allure.step("Verifying Airports");
            Assert.assertTrue(airportNames.contains(airport1), "Airport " + airport1 + " not found");
            Assert.assertTrue(airportNames.contains(airport2), "Airport " + airport2 + " not found");
            Assert.assertTrue(airportNames.contains(airport3), "Airport " + airport3 + " not found");
        } catch (Exception e) {
            Allure.step("Exception encountered while verifying airports: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        log.info("Test \"Verify Specific Airports\" finished");
    }

    // Test case to verify the distance between two airports
    @Test(priority = 30, dataProviderClass = CSVReader.class, dataProvider = "csvDataProvider", description = "Verify Distance Between Airports")
    public void verifyDistanceBetweenAirports(String airportFrom, String airportTo, double expectedDistance) {
        log.info("Starting test \"Verify Distance Between Airports\"");
        try {
            // Prepare the request body for POST request with airport names
            String requestBody = "{\"from\":\"" + airportFrom
                    + "\",\"to\":\"" + airportTo + "\"}";

            // Send POST request to calculate the distance between the two airports
            Response response = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .post("/airports/distance");

            Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

            JsonPath jsonPath = response.jsonPath();
            double distance = jsonPath.getDouble("data.attributes.kilometers");

            Allure.step("Verifying Distance Between Airports");
            Assert.assertTrue(distance > expectedDistance, "Distance should be greater than " + expectedDistance + " km");
        } catch (Exception e) {
            Allure.step("Exception encountered while verifying distance: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        log.info("Test \"Verify Distance Between Airports\" finished");
    }
}
