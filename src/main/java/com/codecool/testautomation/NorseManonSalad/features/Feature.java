package com.codecool.testautomation.NorseManonSalad.features;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
}


