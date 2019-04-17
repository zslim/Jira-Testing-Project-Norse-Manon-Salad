package com.codecool.testautomation.norsemanonsalad.testutils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Utils {

    public static final String BASE_URL = "https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa";
    public static final String NODE_URL = System.getenv("NODE_URL");

    public static void setDriverPath() {
        String driverProperty = System.getenv("DRIVER_PROPERTY");
        String driverPath = System.getenv("DRIVER_PATH");
        if (!StringUtils.isEmpty(driverPath) & !StringUtils.isEmpty(driverProperty)) {
            System.setProperty(driverProperty, driverPath);
        }
    }

    public static WebDriver createDriver() {
        String preferredBrowser = getEnvironmentVar("BROWSER");
        WebDriver driver;
        if (preferredBrowser.equals("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            URL nodeUrl = null;
            try {
                nodeUrl = new URL(NODE_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver = new RemoteWebDriver(nodeUrl, options);
        } else if (preferredBrowser.equals("Firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            URL nodeUrl = null;
            try {
                nodeUrl = new URL(NODE_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver = new RemoteWebDriver(nodeUrl, options);
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
        return string == null ? "" : string;
    }

}
