package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Feature {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int TIMEOUT_FOR_LOADING = 10;

    protected Feature(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT_FOR_LOADING);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void closeDriver() {
        driver.close();
    }

    protected void waitUntilElementLoaded(WebElement webElement) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                wait.until(ExpectedConditions.visibilityOf(webElement));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
            attempts++;
        }
    }

    protected boolean isElementAttributeContains(WebElement webElement, String attribute, String value) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                wait.until(ExpectedConditions.attributeContains(webElement, attribute, value));
                return true;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
            attempts++;
        }
        return false;
    }
}


