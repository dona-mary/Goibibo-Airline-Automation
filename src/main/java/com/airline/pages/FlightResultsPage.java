package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    public FlightResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }
    private final By flightHeader =By.xpath("//span[contains(text(), 'Flights from')]/b[text()='New Delhi']/following-sibling::b[text()='Mumbai']");
    public String getResultsPageUrl() {
    	wait.until(ExpectedConditions.urlContains("search"));
    	System.out.println(driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }
    public String getTravelSummaryText() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(flightHeader));
        return header.getText();
    }
}
