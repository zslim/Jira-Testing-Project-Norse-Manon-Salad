package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

public class BrowseIssues extends Feature {

    @FindBy(id = "find_link")
    WebElement headerIssuesField;

    @FindBy(id = "issues_new_search_link_lnk")
    WebElement searchForIssues;

    @FindBy(xpath = "//*[@id='search-header-view']//h1")
    WebElement searchTitle;

    @FindBy(id = "fieldpid")
    WebElement filteredId;

    @FindBy(xpath = "//div[@data-id='project']")
    WebElement projectFilter;

    @FindBy(id = "searcher-pid-input")
    WebElement projectFilterInputField;

    @FindBy(xpath = "//div[@class='showing']/span")
    WebElement numberOfIssues;

    @FindBy(id = "details-module")
    WebElement detailsButton;

    @FindBy(id = "issuedetails")
    WebElement issueDetails;

    @FindBy(id = "type-val")
    WebElement typeOfIssue;

    @FindBy(className = "issue-list")
    WebElement issuesList;

    @FindBy(css = "span.aui-iconfont-chevron-right")
    WebElement nextPageButton;

    @FindBy(xpath = "//a[@class='refresh-table']//span")
    WebElement refreshPageIcon;

    @FindBy(id = "project-name-val")
    WebElement projectNameValue;


    BrowseIssues(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void displayAllIssues() {
        waitUntilElementLoaded(headerIssuesField);
        headerIssuesField.click();
        waitUntilElementLoaded(searchForIssues);
        searchForIssues.click();
        waitUntilElementLoaded(searchTitle);
    }


    String validateIssuesDisplayed() {
        return searchTitle.getText();
    }

    int getNumOfIssues() {
        String text = numberOfIssues.getText();
        String[] split = text.split(" ");
        int max = Integer.parseInt(split[2]);
        return max;
    }


    void searchIssueByName(String projectName) {
        waitUntilElementClickable(projectFilter);
        projectFilter.click();
        waitUntilElementClickable(projectFilterInputField);
        projectFilterInputField.sendKeys(projectName + Keys.ENTER + Keys.ESCAPE);
        wait.until(ExpectedConditions.textToBePresentInElement(projectNameValue, projectName));
    }


    boolean validateNumberOfIssues(String project, int minimal) {
        displayAllIssues();
        searchIssueByName(project);
        wait.until(ExpectedConditions.textToBePresentInElement(filteredId, project));
        if (getNumOfIssues() <= minimal) {
            return false;
        } else {
            return true;
        }
    }


    String[] validateIssueDetail() {
        List<WebElement> details = issueDetails.findElements(By.cssSelector("li.item strong"));
        String[] detailTitles = new String[details.size()];
        for (int i = 0; i < details.size(); i++) {
            detailTitles[i] = details.get(i).getText();
        }
        return detailTitles;
    }

    void seeDetailsOfIssue(String projectName) {
        displayAllIssues();
        searchIssueByName(projectName);
        waitUntilElementClickable(detailsButton);
        String collapsedButtonClass = "module toggle-wrap collapsed";
        if (detailsButton.getAttribute("class").equals(collapsedButtonClass)) {
            detailsButton.click();
        }
        waitUntilElementClickable(typeOfIssue);
    }


    int getNumOfIssuesByPagination() {
        waitUntilElementLoaded(issuesList);
        int issueCounter = 0;
        boolean nextPageClickable = true;
        while (nextPageClickable) {
            List<WebElement> issuesPerPage = issuesList.findElements(By.tagName("li"));
            int size = issuesPerPage.size();
            issueCounter += size;

            if (isElementPresent(nextPageButton)) {
                waitUntilElementClickable(refreshPageIcon);
                nextPageButton.click();
            } else {
                nextPageClickable = false;
            }
        }
        return issueCounter;
    }

}
