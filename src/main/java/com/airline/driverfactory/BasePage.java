package com.airline.driverfactory;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }

    // Wait until an element is visible
    protected WebElement waitForVisibility(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait until an element is clickable
    protected WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    // Enter text into an input field
    protected void enterText(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Click an element
    protected void click(By locator) {
        waitForClickable(locator).click();
    }

    // Get visible text
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    // Check whether an element is displayed
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    // Retrieve all matching elements
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    // Wait until a URL contains the expected text
    protected boolean waitForUrlContains(String expectedText) {
        return wait.until(
                ExpectedConditions.urlContains(expectedText));
    }

    // Scroll an element into view
    protected void scrollIntoView(By locator) {
        WebElement element = waitForVisibility(locator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element);
    }

    // Get the page title
    protected String getPageTitle() {
        return driver.getTitle();
    }
}
