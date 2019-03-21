package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    List<String> getProjectTypes(String projectName, List<String> requiredTypes) {
        List<String> actualResults = new ArrayList<>();

        selectFromDropdown(projectField, projectName);

        WebDriverWait wait = new WebDriverWait(driver,10);

        for (String type:
             requiredTypes) {
            wait.until(ExpectedConditions.elementToBeClickable(issueTypeField));

            issueTypeField.sendKeys(type);

            if (!issueTypeField.getAttribute("aria-activedescendant").equals("null")) {
                issueTypeField.sendKeys(Keys.ENTER);
                actualResults.add(type);
            }
        }
        return actualResults;
    }

    //Delete issue after test
    void deleteIssue(String issueName) {
        driver.navigate().to("https://jira.codecool.codecanvas.hu/secure/Dashboard.jspa");

        driver.findElement(By.id("find_link")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
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

    boolean validateSuccessfulIssueCreation(String issueName) {
        waitUntilElementLoaded(conformationContainer);

        if (conformationLink.getText().contains(issueName)) {
            return true;
        } else {
            return false;
        }
    }

    boolean validateEmptySummaryError() {
        waitUntilElementLoaded(errorMessage);

        if (errorMessage.getAttribute("data-field").equals("summary")) {
            return true;
        } else {
            return false;
        }
    }
}
