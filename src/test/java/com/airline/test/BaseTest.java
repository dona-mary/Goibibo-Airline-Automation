package com.airline.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.airline.core.DriverFactory;

public class BaseTest {
    //protected DriverFactory driverFactory;
    
    protected static Properties prop; // Made static so all tests share the same loaded configuration

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {
    	  System.setProperty("webdriver.chrome.silentOutput", "true");
    	    java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        // Fix 1: Initialize and load the properties file once before any tests start
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("./src/main/resources/config/config.properties"); // Adjust file path as needed
            prop.load(fis);
        } catch (IOException e) {
            System.err.println("Configuration file could not be loaded. Default values will be used.");
        }
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    // Fix 2: Accept headless as a String and add @Optional to prevent failures when running single tests directly
    public void setUp(@Optional String browser, @Optional String headless) {
        
        // Evaluate browser: TestNG XML -> Properties File -> Hardcoded fallback
        String br = (browser != null && !browser.isEmpty()) 
                ? browser 
                : prop.getProperty("browser", "chrome");
        
        // Evaluate headless: TestNG XML -> Properties File -> Hardcoded fallback
        boolean isHeadless = (headless != null && !headless.isEmpty())
                ? Boolean.parseBoolean(headless)
                : Boolean.parseBoolean(prop.getProperty("headless", "false"));
    
        
        WebDriver driver = DriverFactory.initDriver(br, isHeadless);

        driver.get(prop.getProperty("baseUrl"));
    }
    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
