package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    boolean validateSuccessfulIssueCreation(String issueName) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aui-flag-container")));

        WebElement conformation = driver.findElement(By.xpath("//a[@class='issue-created-key issue-link']"));

        if (conformation.getText().contains(issueName)) {
            return true;
        } else {
            return false;
        }
    }
}
