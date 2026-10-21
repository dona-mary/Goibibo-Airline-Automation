package com.airline.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    
    private final By closePopupButton =By.cssSelector(
            "span.logSprite.icClose");

    private final By sourceInput = By.id("fromCity");
    private final By sourceCity=By.xpath("//div[contains(@class,'revampedSuggestionContentColumn')][.//span[contains(@class,'revampedCityName') and contains(normalize-space(.),'New Delhi, India')]]");     
    private final By dateSelector =
            By.xpath("(//div[@class='dateInnerCell'])[54]");
    private final By destinationInput = By.id("toCity");
    private final By selectCity= By.xpath("//div[contains(@class,'revampedSuggestionContentColumn')][.//span[contains(@class,'revampedCityName') and contains(normalize-space(.),'Mumbai, India')]]");     
    private final By searchButton =
            By.cssSelector("[data-cy='submit']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }
    public void alertDismiss() {
    	Alert alert=wait.until(
                ExpectedConditions.alertIsPresent());
    	alert.dismiss();
    	
    }
    public void open(String baseUrl) {
        driver.get(baseUrl);
    }
    public void closePopup() {
    	 
          try {
              // Using a bit longer wait for optional popup
              WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
              WebElement closeButton=wait.until(ExpectedConditions.elementToBeClickable(closePopupButton));
              if ( closeButton.isDisplayed()) {
            	  closeButton.click();
              }
          } catch (Exception e) {
              System.out.println("Login popup not found or not clickable: " + e.getMessage());
          }
      }

    

    public void enterSource(String source) {
        //wait.until(ExpectedConditions.visibilityOfElementLocated(sourceInput)).clear();
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(sourceInput)
//            ).sendKeys(source);
    	WebElement sourceField = wait.until(
		        ExpectedConditions.elementToBeClickable(sourceInput)
		    );

		   
		    sourceField.sendKeys(source);

		   
		    WebDriverWait suggestionWait =
		        new WebDriverWait(driver, Duration.ofSeconds(10));

		    suggestionWait.ignoring(StaleElementReferenceException.class);

		    for (int attempt = 1; attempt <= 3; attempt++) {
		        try {
		            WebElement cityOption = suggestionWait.until(
		                ExpectedConditions.elementToBeClickable(sourceCity)
		            );

		            cityOption.click();
		            return;

		        } catch (StaleElementReferenceException e) {
		            if (attempt == 3) {
		                throw e;
		            }
		        }
		    }
    }

    public void enterDestination(String destination) {
//        //wait.until( ExpectedConditions.visibilityOfElementLocated(destinationInput)).clear();
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(destinationInput)
//            ).sendKeys(destination);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selectCity));
//     // Scrolls the element smoothly to the exact center of the page viewport
//     js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
//
//     // Give a minor buffer for smooth scrolling to complete if needed
//     element.click();
    	 WebElement destinationField = wait.until(
    		        ExpectedConditions.elementToBeClickable(destinationInput)
    		    );

    		    //destinationField.clear();
    		    destinationField.sendKeys(destination);

    		   
    		    WebDriverWait suggestionWait =
    		        new WebDriverWait(driver, Duration.ofSeconds(10));

    		    suggestionWait.ignoring(StaleElementReferenceException.class);

    		    for (int attempt = 1; attempt <= 3; attempt++) {
    		        try {
    		            WebElement cityOption = suggestionWait.until(
    		                ExpectedConditions.elementToBeClickable(selectCity)
    		            );

    		            cityOption.click();
    		            return;

    		        } catch (StaleElementReferenceException e) {
    		            if (attempt == 3) {
    		                throw e;
    		            }
    		        }
    		    }
        
    }
    public void enterDepDate() {
		wait.until(
			ExpectedConditions.visibilityOfElementLocated(dateSelector)).click();
	}
    public void searchFlights() {
        wait.until(
            ExpectedConditions.elementToBeClickable(searchButton)
        ).click();
    }
}