package com.codecool.testautomation.NorseManonSalad.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login extends Feature {

    private static final String BASE_URL = "https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa";

    @FindBy(id = "login-form-username")
    WebElement userNameField;

    @FindBy(id = "login-form-password")
    WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"header-details-user-fullname\"]//img")
    WebElement userProfilePicture;

    @FindBy(id = "usernameerror")
    WebElement errorMessage;

    @FindBy(id = "login")
    WebElement loginSubmitButton;


    protected Login(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void login(String userName, String password) {
        driver.get(BASE_URL);
        userNameField.sendKeys(userName);
        passwordField.sendKeys(password);
        passwordField.submit();
    }

    String validateProfile() {
        waitUntilElementLoaded(userProfilePicture);
        String profilePictureAltString = userProfilePicture.getAttribute("alt");
        return profilePictureAltString;
    }

    boolean validateErrorMessage() {
        waitUntilElementLoaded(loginSubmitButton);
        boolean isPresentErrorMessage = errorMessage.isDisplayed();
        return isPresentErrorMessage;
    }
}
