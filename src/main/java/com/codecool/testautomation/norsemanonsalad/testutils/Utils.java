package com.codecool.testautomation.norsemanonsalad.testutils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Objects;

public class Utils {

    public static final String BASE_URL = "https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa";

    public static void setDriverPath() {
        String driverProperty = getEnvironmentVar("DRIVER_PROPERTY");
        String driverPath = getEnvironmentVar("DRIVER_PATH");
        System.setProperty(driverProperty, driverPath);
    }

    public static WebDriver createDriver() throws MalformedURLException{
        DesiredCapabilities desiredCapabilities;
        String preferredBrowser = getEnvironmentVar("BROWSER");
        WebDriver driver;
        if (preferredBrowser.equals("Chrome")) {
            desiredCapabilities = DesiredCapabilities.chrome();
            desiredCapabilities.setBrowserName("chrome");
            driver = new RemoteWebDriver(new URL("http://selenium:CCPass123@selenium.codecool.codecanvas.hu:4444/wd/hub"), desiredCapabilities);
        } else if (preferredBrowser.equals("Firefox")) {
            desiredCapabilities = DesiredCapabilities.firefox();
            desiredCapabilities.setBrowserName("firefox");
            driver = new RemoteWebDriver(new URL("http://selenium:CCPass123@selenium.codecool.codecanvas.hu:4444/wd/hub"), desiredCapabilities);
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
