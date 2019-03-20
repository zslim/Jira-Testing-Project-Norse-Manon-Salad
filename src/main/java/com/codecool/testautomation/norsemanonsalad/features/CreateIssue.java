package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateIssue extends Feature {

    @FindBy(id = "create_link")
    WebElement createButton;

    @FindBy(id = "create-issue-submit")
    WebElement submitIssueButton;

    @FindBy(xpath = "//a[@class='cancel']")
    WebElement cancelButton;

    @FindBy(id = "qf-create-another")
    WebElement createAnotherCheckbox;

    @FindBy(id = "summary")
    WebElement summaryField;

    @FindBy(id = "project-field")
    WebElement projectField;

    @FindBy(id = "issuetype-field")
    WebElement issueTypeField;

    @FindBy(xpath = "//a[@class='issue-created-key issue-link']")
    WebElement conformationLink;

    @FindBy(id = "aui-flag-container")
    WebElement conformationContainer;

    @FindBy(xpath = "//div[@class='aui-message-context']//a")
    WebElement conformationWithCheckbox;

    @FindBy(xpath = "//div[@class='error']")
    WebElement errorMessage;

    public CreateIssue(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void clickCreateButton() {
        createButton.click();

        waitUntilElementLoaded(submitIssueButton);
    }

    void clickCancelButton() {
        cancelButton.click();
    }

    void selectFromDropdown(WebElement element, String select) {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.sendKeys(select);

        if (!element.getAttribute("aria-activedescendant").equals("null")) {
            element.sendKeys(Keys.ENTER);
        } else {
            throw new IllegalArgumentException();
        }
    }

    void fillInSummaryField(String summaryMessage) {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(summaryField));

        summaryField.sendKeys(summaryMessage);
    }

    void submitNewIssue() {
        submitIssueButton.click();
    }

    void createNewIssue(String projectName, String issueName, String summary) {
        selectFromDropdown(projectField, projectName);
        selectFromDropdown(issueTypeField, issueName);
        fillInSummaryField(summary);
        submitNewIssue();
    }

    void cancelIssueCreation(String projectName, String issueName, String summary) {
        selectFromDropdown(projectField, projectName);
        selectFromDropdown(issueTypeField, issueName);
        fillInSummaryField(summary);
        clickCancelButton();
    }

    boolean validateSuccessfulIssueCreation(String issueName) {
        waitUntilElementLoaded(conformationContainer);

        if (conformationLink.getText().contains(issueName)) {
            return true;
        } else {
            return false;
        }
    }
}
