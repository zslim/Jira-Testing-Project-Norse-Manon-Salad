package com.codecool.testautomation.NorseManonSalad.TestUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Utils {

    public static final String BASE_URL = "https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa";

    public static void setDriverPath() {
        String driverProperty = System.getenv("DRIVER_PROPERTY");
        String driverPath = System.getenv("DRIVER_PATH");
        System.setProperty(driverProperty, driverPath);
    }

    public static WebDriver createDriver(){
        WebDriver driver = new FirefoxDriver();
        return driver;
    }

}
