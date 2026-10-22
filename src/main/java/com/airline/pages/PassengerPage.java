package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.airline.driverfactory.BasePage;

public class PassengerPage extends BasePage {
	
    private final WebDriverWait wait;
    
    public PassengerPage(WebDriver driver) {
    	super(driver);
		this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }
    private final By addTraveller =By.cssSelector("button.addTravellerBtn");
    private final By firstName = By.cssSelector("input[placeholder='First & Middle Name']");
    private final By lastName =By.cssSelector("input[placeholder='Last Name']");
    private final By email = By.cssSelector("input[placeholder='Email']");
    private final By mobile = By.cssSelector("input[placeholder='Mobile No']");
    private final By submitButton = By.xpath("//button[text()='Continue']");
    
    public void fillPassengerDetails(String firstName, String lastName, String email, String mobile) {
		 wait.until(ExpectedConditions.elementToBeClickable(addTraveller)).click();
		 enterText(this.firstName, firstName);
		 enterText(this.lastName, lastName);
		 enterText(this.email, email);
		 enterText(this.mobile, mobile);
		 scrollIntoView(submitButton);
		 wait.until(ExpectedConditions.elementToBeClickable(this.submitButton)).click();
	}
	    
}

