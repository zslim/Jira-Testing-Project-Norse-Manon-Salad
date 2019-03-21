package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

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

    @FindBy(id = "aui-flag-container")
    WebElement conformationContainer;

    @FindBy(xpath = "//div[@class='aui-message-context']//a")
    WebElement conformationForCreateAnother;

    @FindBy(xpath = "//div[@class='error']")
    WebElement errorMessage;

    @FindBy(id = "find_link")
    WebElement issues;

    @FindBy(id = "find_link-content")
    WebElement searchForIssuesContainer;

    @FindBy(id = "issues_new_search_link_lnk")
    WebElement searchForIssuesLink;

    @FindBy(id = "searcher-query")
    WebElement searchField;

    @FindBy(xpath = "//*[@id='delete-issue']/a")
    WebElement deleteLink;

    @FindBy(id = "delete-issue-submit")
    WebElement deleteButtonOnPopup;

    @FindBy(id = "opsbar-operations_more")
    WebElement moreOperationsButton;

    public CreateIssue(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void clickCreateButton() {
        createButton.click();

        waitUntilElementLoaded(submitIssueButton);
    }

    void selectFromDropdown(WebElement element, String select) {
        wait.until(ExpectedConditions.elementToBeClickable(element));

        element.sendKeys(select);

        if (!element.getAttribute("aria-activedescendant").equals("null")) {
            element.sendKeys(Keys.ENTER);
        } else {
            throw new IllegalArgumentException(select + " cannot be found!");
        }
    }

    void fillInSummaryField(String summaryMessage) {
        wait.until(ExpectedConditions.elementToBeClickable(summaryField));

        summaryField.sendKeys(Utils.nullToEmptyString(summaryMessage));
    }

    void createNewIssue(String projectName, String issueName, String summary) {
        selectFromDropdown(projectField, projectName);
        selectFromDropdown(issueTypeField, issueName);
        fillInSummaryField(summary);
        submitIssueButton.click();
    }

    List<String> getRequiredTypes() {
        List<String> requiredTypes = new ArrayList<>();
        requiredTypes.add("Story");
        requiredTypes.add("Task");
        requiredTypes.add("Bug");
        requiredTypes.add("Sub-task");

        return requiredTypes;
    }

    String getProjectTypes(String projectName, List<String> requiredTypes) {
        List<String> actualResults = new ArrayList<>();

        selectFromDropdown(projectField, projectName);

        for (String type:
             requiredTypes) {
            wait.until(ExpectedConditions.elementToBeClickable(issueTypeField));

            issueTypeField.sendKeys(type);

            if (!issueTypeField.getAttribute("aria-activedescendant").equals("null")) {
                actualResults.add(type);
                issueTypeField.sendKeys(Keys.ENTER);
            } else {
                issueTypeField.clear();
            }
        }
        return actualResults.toString();
    }

    boolean validateSuccessfulIssueCreation() {
        try {
            waitUntilElementLoaded(conformationContainer);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    boolean validateEmptySummaryError() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void navigateToSearchForIssues() {
        driver.navigate().to("https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa");

        issues.click();

        wait.until(ExpectedConditions.visibilityOf(searchForIssuesContainer));

        searchForIssuesLink.click();
    }

    void fillInSearchField(String issueName) {
        wait.until(ExpectedConditions.elementToBeClickable(searchField));

        searchField.sendKeys(issueName);
        searchField.sendKeys(Keys.ENTER);
    }

    void deleteIssue(String projectName, String issueName) {
        navigateToSearchForIssues();

        fillInSearchField(issueName);

        wait.until(ExpectedConditions.textToBe(By.id("project-name-val"), projectName));

        moreOperationsButton.click();

        Actions action = new Actions(driver);

        action.moveToElement(deleteLink);
        action.perform();

        wait.until(ExpectedConditions.visibilityOf(deleteLink));

        deleteLink.click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteButtonOnPopup));

        deleteButtonOnPopup.click();
    }
}
