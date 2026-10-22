package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.airline.driverfactory.BasePage;

public class ConfirmationPage extends BasePage {
	   
	    private final WebDriverWait wait;
	    public ConfirmationPage(WebDriver driver) {
	        super(driver);
	        this.wait = new WebDriverWait(
	                driver, Duration.ofSeconds(10));
	    }
}