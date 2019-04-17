package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Feature {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int TIMEOUT_FOR_LOADING = Integer.parseInt(Utils.getEnvironmentVar("TIMEOUT_FOR_LOADING"));
    private static final int MAX_ATTEMPT = Integer.parseInt(Utils.getEnvironmentVar("MAX_ATTEMPT"));

    protected Feature(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT_FOR_LOADING);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void closeDriver() {
        driver.quit();
    }

    protected void waitUntilElementLoaded(WebElement webElement) {
        int attempt = 0;
        while (attempt < MAX_ATTEMPT) {
            try {
                wait.until(ExpectedConditions.visibilityOf(webElement));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
            attempt++;
        }
    }

    protected boolean doesContainAttribute(WebElement webElement, String attribute, String value) {
        int attempt = 0;
        while (attempt < MAX_ATTEMPT) {
            try {
                wait.until(ExpectedConditions.attributeContains(webElement, attribute, value));
                return true;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
            attempt++;
        }
        return false;
    }

    protected boolean isElementPresent(WebElement webElement) {
        int attempt = 0;
        while (attempt < MAX_ATTEMPT) {
            try {
                wait.until(ExpectedConditions.visibilityOf(webElement));
                return true;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
            attempt++;
        }
        return false;
    }

    protected void waitUntilElementClickable(WebElement webElement) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPT) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(webElement));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
            attempts++;
        }
    }
}


