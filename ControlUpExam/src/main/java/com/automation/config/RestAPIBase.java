package com.automation.config;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class RestAPIBase {

    private static final String BASE_URL = "https://airportgap.com/api";

    @BeforeClass
    @Step("Initialize the base URI for RestAssured")
    public void setUpAPI() {
        RestAssured.baseURI = BASE_URL;
    }
}
