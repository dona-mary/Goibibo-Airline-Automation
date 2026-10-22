package com.airline.test;

import com.airline.driverfactory.DriverFactory;
import com.airline.pages.ConfirmationPage;
import com.airline.pages.FlightResultsPage;
import com.airline.pages.HomePage;
import com.airline.pages.PassengerPage;
import com.airline.pages.PaymentPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FlightBookingE2ETest extends BaseTest {

    @Test
    public void searchFlightsWithValidRoute() {
    	 HomePage homePage = new HomePage(DriverFactory.getDriver());

        homePage.open("https://www.goibibo.com/");
        homePage.closePopup();
         homePage.enterSource("Delhi");
        homePage.enterDestination("Mumbai");;
        homePage.enterDepDate();
        homePage.searchFlights();

        FlightResultsPage flightResultsPage = new FlightResultsPage(DriverFactory.getDriver());
		String actualUrl = flightResultsPage.getResultsPageUrl();
        Assert.assertTrue(actualUrl.contains("search"), "FAIL: Driver was not redirected to the search results route context.");

        // Step 3: Assert Travel Summary Header contains correct cities
        String expectedHeaderFragment1 = "New Delhi";
        String expectedHeaderFragment2 = "Mumbai";
        String actualHeaderText = flightResultsPage.getTravelSummaryText();

        Assert.assertTrue(actualHeaderText.contains(expectedHeaderFragment1), 
            "FAIL: Header does not contain origin city: " + expectedHeaderFragment1);
        Assert.assertTrue(actualHeaderText.contains(expectedHeaderFragment2), 
            "FAIL: Header does not contain destination city: " + expectedHeaderFragment2);
        
        PassengerPage passengerPage = new PassengerPage(DriverFactory.getDriver());
        passengerPage.fillPassengerDetails("Kishore", "varma", "dummydummy@gmail.com", "9566445656");
        
        PaymentPage paymentPage = new PaymentPage(DriverFactory.getDriver());
        paymentPage.paymentProcess("1234566546");
        
        ConfirmationPage confirmationPage = new ConfirmationPage(DriverFactory.getDriver());
        
    }
 }