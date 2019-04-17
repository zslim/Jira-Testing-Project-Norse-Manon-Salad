package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login extends Feature {

    @FindBy(id = "login-form-username")
    WebElement userNameField;

    @FindBy(id = "login-form-password")
    WebElement passwordField;

    @FindBy(xpath = "//*[@id='header-details-user-fullname']")
    WebElement userProfileIcon;

    @FindBy(id = "login")
    WebElement loginSubmitButton;

    final String USER_NAME = Utils.getEnvironmentVar("USER_NAME");
    final String PASSWORD = Utils.getEnvironmentVar("PASSWORD");

    protected Login(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void login(String userName, String password) {
        driver.get(Utils.BASE_URL);
        waitUntilElementLoaded(userNameField);

        userNameField.sendKeys(Utils.nullToEmptyString(userName));
        passwordField.sendKeys(Utils.nullToEmptyString(password));
        passwordField.submit();
    }

    void loginSuccessful() {
        login(USER_NAME, PASSWORD);
        waitUntilElementLoaded(userProfileIcon);
    }

    String validateSuccessfulLogin() {
        waitUntilElementLoaded(userProfileIcon);
        String profileIconUsername = userProfileIcon.getAttribute("data-username");
        return profileIconUsername;
    }

    boolean validateErrorMessage(String errorId) {
        waitUntilElementLoaded(loginSubmitButton);
        WebElement errorMessage = driver.findElement(By.id(errorId));
        boolean isPresentErrorMessage = errorMessage.isDisplayed();
        return isPresentErrorMessage;
    }
}
