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

    //Delete issue after test - should be in a different class
    void deleteIssue(String issueName) {
        driver.navigate().to("https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa");

        driver.findElement(By.id("find_link")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("find_link-content")));

        driver.findElement(By.id("issues_new_search_link_lnk")).click();

        WebElement searchField = driver.findElement(By.id("searcher-query"));

        wait.until(ExpectedConditions.elementToBeClickable(searchField));

        searchField.sendKeys(issueName);
        searchField.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.textToBe(By.id("project-name-val"), "Private Project 3"));

        driver.findElement(By.id("opsbar-operations_more")).click();

        WebElement delete = driver.findElement(By.xpath("//*[@id='delete-issue']/a"));
        Actions action = new Actions(driver);

        action.moveToElement(delete);
        action.perform();

        wait.until(ExpectedConditions.visibilityOf(delete));

        delete.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("delete-issue-submit")));

        driver.findElement(By.id("delete-issue-submit")).click();
    }
}
