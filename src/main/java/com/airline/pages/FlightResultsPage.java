package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.airline.core.BasePage;

public class FlightResultsPage extends BasePage {
	
    private final WebDriverWait wait;
    
    public FlightResultsPage(WebDriver driver) {
    	super(driver);
		this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }
    private final By flightHeader =By.xpath("//span[contains(text(), 'Flights from')]/b[text()='New Delhi']/following-sibling::b[text()='Mumbai']");
    private final By viewFareButton = By.xpath("(//button[contains(@id,'bookbutton')])[1]");
    private final By bookFlightButton = By.xpath("//button[text()='BOOK NOW']");
  //div[contains(@class,'travellerHeading')]
    public String getResultsPageUrl() {
    	wait.until(ExpectedConditions.urlContains("search"));
    	System.out.println(driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }
    public String getTravelSummaryText() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(flightHeader));
        return header.getText();
    }
    
    public void selectFlight() {
		WebElement flightButton = wait.until(ExpectedConditions.elementToBeClickable(viewFareButton));
		flightButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(bookFlightButton)).click();
	}
}
