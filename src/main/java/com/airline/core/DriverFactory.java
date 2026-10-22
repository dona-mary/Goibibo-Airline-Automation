package com.airline.core;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;



public class DriverFactory {
	
	private static final ThreadLocal<WebDriver> DRIVER =new ThreadLocal<>();

	      	public static  WebDriver initDriver(String browser, boolean headless) {
	    		
	    		if (browser == null) browser = "chrome";
	    		
	    		switch (browser.toLowerCase()) {
	    		
	    		case "chrome":
	    			
	    			ChromeOptions co = new ChromeOptions();
	    			if (headless) co.addArguments("--headless=new");
	    			// Prevents Chrome from revealing 'navigator.webdriver = true'
	    			co.addArguments("--disable-blink-features=AutomationControlled"); 

	    			// Hides the "Chrome is being controlled by automated software" banner
	    			
	    			
	    			co.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
	    			co.setExperimentalOption("useAutomationExtension", false);

	    			// 4. Run in maximize window to mimic an actual monitor footprint
	    			co.addArguments("--start-maximized");
	    			 
	    			co.addArguments("--disable-notifications");

	    			co.addArguments("--no-sandbox");
	    			co.addArguments("--disable-dev-shm-usage");
	    			co.addArguments("--disable-gpu");
	    			co.addArguments("--window-size=1920,1080");
	    			//co.addArguments("--start-maximized");
	    			DRIVER.set(new ChromeDriver(co));
	    			break;
	    		
	    		case "edge":
	    			
	    			EdgeOptions eo = new EdgeOptions();
	    			if (headless) eo.addArguments("--headless=new");
	    			eo.addArguments("--start-maximized");
	    			DRIVER.set(new EdgeDriver(eo));
	    			break;
	    			
	    		case "firefox":
	    		
	    			FirefoxOptions fo = new FirefoxOptions();
	    			if (headless) fo.addArguments("--headless=new");
	    			fo.addArguments("--start-maximized");
	    			DRIVER.set(new FirefoxDriver(fo));
	    			break;
	    			
	    		default:
	    			throw new IllegalArgumentException("Unsupported browser: " + browser);
	    		
	    		}
	    		
	    		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	    		
	    		return getDriver();
	    	}
	    	
	    

	    public static WebDriver getDriver() {
	        WebDriver driver = DRIVER.get();

	        if (driver == null) {
	            throw new IllegalStateException(
	                "WebDriver has not been initialized for this thread");
	        }

	        return driver;
	    }

	    public static void quitDriver() {
	        WebDriver driver = DRIVER.get();

	        try {
	            if (driver != null) {
	                driver.quit();
	            }
	        } finally {
	            DRIVER.remove();
	        }
	    }
	}


