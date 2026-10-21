package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage {
	   private final WebDriver driver;
	    private final WebDriverWait wait;
	    public ConfirmationPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(
	                driver, Duration.ofSeconds(10));
	    }
}