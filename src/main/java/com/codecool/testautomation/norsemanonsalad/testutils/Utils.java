package com.codecool.testautomation.norsemanonsalad.testutils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Objects;

public class Utils {

    public static final String BASE_URL = "https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa";

    public static void setDriverPath() {
        String driverProperty = getEnvironmentVar("DRIVER_PROPERTY");
        String driverPath = getEnvironmentVar("DRIVER_PATH");
        System.setProperty(driverProperty, driverPath);
    }

    public static WebDriver createDriver(){
        String preferredBrowser = getEnvironmentVar("BROWSER");
        WebDriver driver;
        if (preferredBrowser.equals("Chrome")) {
            driver = new ChromeDriver();
        } else if (preferredBrowser.equals("Firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new NotSupportedBrowserException("Browser specified in env var not supported, please choose Chrome or Firefox");
        }

        return driver;
    }

    public static String getEnvironmentVar(String name) {
        String var = System.getenv(name);
        if (StringUtils.isEmpty(var)) {
            throw new EnvironmentalVariableNotFoundException("Environmental variable " + name + " empty or nonexistent");
        } else {
            return var;
        }
    }

    public static String nullToEmptyString(String string) {
        return Objects.requireNonNullElse(string, "");
    }

}
