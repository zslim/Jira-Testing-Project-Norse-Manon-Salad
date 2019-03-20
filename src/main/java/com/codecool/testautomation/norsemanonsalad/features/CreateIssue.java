package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateIssue extends Feature {

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

    public CreateIssue(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void selectProject(String projectName) {
        projectField.sendKeys(projectName);

        if (projectField.getAttribute("aria-activedescendant").equals("null")) {
            throw new IllegalArgumentException();
        }
    }

    void selectIssueType(String issueName) {
        issueTypeField.sendKeys(issueName);

        if (issueTypeField.getAttribute("aria-activedescendant").equals("null")) {
            throw new IllegalArgumentException();
        }
    }

    void fillInSummaryField(String summaryMessage) {
        summaryField.sendKeys(summaryMessage);
    }

    void submitNewIssue() {
        submitIssueButton.click();
    }

    void createNewIssue(String projectName, String issueName, String summary) {
        selectProject(projectName);
        selectIssueType(issueName);
        fillInSummaryField(summary);
        submitNewIssue();
    }
}
