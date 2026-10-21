package com.airline.test;


import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import com.airline.driverfactory.APIEndpoints;

import static io.restassured.RestAssured.*;


public class FlightApiTest extends BaseTest {

    @Test
    public void testSearchFlight() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("source", "DEL")
            .queryParam("destination", "BOM")
        .when()
            // Reference the global endpoint constant path variable
            .get(APIEndpoints.FLIGHT_SEARCH) 
        .then()
            .statusCode(200);
    }
}

//url: "https://developer.goibibo.com/api/search/",
//type: "POST",
//data: {
//  app_id: "your_app_id",
//  app_key: "your_app_key",
//  format: "json",
//  source: "BLR",
//  destination: "DEL",
//  dateofdeparture: "20211120",
//  seatingclass: "E",
//  adults: "1",
//  children: "0",
//  infants: "0",
//  counter: "100"
//},
//success: function(data) {
//  console.log(data);
//}