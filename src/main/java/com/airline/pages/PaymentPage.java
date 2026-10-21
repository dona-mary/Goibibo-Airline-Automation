package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPage {
	 private final WebDriver driver;
	    private final WebDriverWait wait;
	    	    
	    public PaymentPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(
	                driver, Duration.ofSeconds(10));
	    }

}
