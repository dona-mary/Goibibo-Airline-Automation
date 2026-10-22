package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.airline.driverfactory.BasePage;


public class PaymentPage extends BasePage {
	 
	    private final WebDriverWait wait;
	    	    
	    public PaymentPage(WebDriver driver) {
	        super(driver);
	        this.wait = new WebDriverWait(
	                driver, Duration.ofSeconds(10));
	    }
	    private final By cardNumber =By.id("card_number");
		private final By payNowButton = By.xpath("//button[text()='PAY NOW']");

	     public void paymentProcess(String card) {
	        enterText(cardNumber, card);
	        click(payNowButton);
	    }


}
