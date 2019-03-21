package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class BrowseIssues extends Feature {

    @FindBy(id = "find_link")
    WebElement headerIssuesField;

    @FindBy(id = "issues_new_search_link_lnk")
    WebElement searchForIssues;

    @FindBy(xpath = "//*[@id=\"search-header-view\"]/div/h1")
    WebElement searchTitle;

    @FindBy(id = "fieldpid")
    WebElement filteredId;

    @FindBy(xpath = "//DIV[@data-id='project']")
    WebElement projectFilter;

    @FindBy(id = "searcher-pid-input")
    WebElement projectFilterInputField;

    @FindBy(xpath = "//*[@id=\"content\"]/div[1]/div[3]/div/div/div/div/div/div/div/div[2]/div[2]/div/div[1]/div/div[1]/span")
    WebElement numberOfIssues;

    @FindBy(id = "details-module")
    WebElement detailsButton;

    @FindBy(id = "issuedetails")
    WebElement issueDetails;

    @FindBy(id = "type-val")
    WebElement typeOfIssue;

    @FindBy(className = "issue-list")
    WebElement issuesList;

    @FindBy(xpath = "//SPAN[@class='aui-icon aui-icon-small aui-iconfont-chevron-right'][contains(text(),'Next ')]")
    WebElement nextPageIcon;

    @FindBy(xpath = "//SPAN[@class='aui-icon aui-icon-small aui-iconfont-refresh-small'][text()='Refresh results']")
    WebElement refreshPageIcon;


    Login login;

    BrowseIssues(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
        login = new Login(driver);
    }

    void displayAllIssues(){
        waitUntilElementLoaded(headerIssuesField);
        headerIssuesField.click();
        waitUntilElementLoaded(searchForIssues);
        searchForIssues.click();
        waitUntilElementLoaded(searchTitle);
    }


    String validateIssuesDisplayed(){
        return searchTitle.getText();
    }

    int getNumOfIssues(){
        String text = numberOfIssues.getText();
        String[] split = text.split(" ");
        int max = Integer.parseInt(split[2]);
        return max;
    }


    void searchIssueByName(String projectName){
        waitUntilElementClickable(projectFilter);
        projectFilter.click();
        waitUntilElementClickable(projectFilterInputField);
        projectFilterInputField.sendKeys(projectName + Keys.ENTER + Keys.ESCAPE);
    }


    boolean getNumOfIssuesPerProject(String[] projects, int minimal){
        Map<String, Integer > issuesPerProject = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean minimalNumOfIssuesExist = true;

        for (int i = 0; i < projects.length; i++) {
            displayAllIssues();
            searchIssueByName(projects[i]);
            wait.until(ExpectedConditions.textToBePresentInElement(filteredId, projects[i]));
            issuesPerProject.put(projects[i], getNumOfIssues());
            if (getNumOfIssues() < minimal) {
                minimalNumOfIssuesExist = false;
            }
        }
        System.out.println(Arrays.toString(issuesPerProject.entrySet().toArray()));
        return minimalNumOfIssuesExist;
    }


    Map<String, String> getDetailesOfIssue(String projectName){
        searchIssueByName(projectName);
        waitUntilElementClickable(detailsButton);
        if (detailsButton.getAttribute("class").equals("module toggle-wrap collapsed")){
            detailsButton.click();
        }
        waitUntilElementClickable(typeOfIssue);
        List<WebElement> details = issueDetails.findElements(By.tagName("li"));
        Map<String, String> detailsText = new HashMap<>();
        for (WebElement detail : details) {
            detailsText.put(detail.findElement(By.tagName("strong")).getText(), detail.findElement(By.tagName("span")).getText());
        }
        System.out.println("Details: \n" + detailsText);
        return detailsText;
    }


    int getNumOfIssuesByPagination(){
        waitUntilElementLoaded(issuesList);
        int issueCounter = 0;
        boolean nextPageClickable = true;

        while (nextPageClickable){
            waitUntilElementClickable(refreshPageIcon);
            if (driver.findElements( By.xpath("//SPAN[@class='aui-icon aui-icon-small aui-iconfont-chevron-right'][contains(text(),'Next ')]")).size() != 0 ) {
                waitUntilElementLoaded(issuesList);
                List<WebElement> issuesPerPage = issuesList.findElements(By.tagName("li"));
                int size = issuesPerPage.size();
                issueCounter += size;
                waitUntilElementClickable(nextPageIcon);
                waitUntilElementClickable(refreshPageIcon);
                nextPageIcon.click();
            }else {
                nextPageClickable = false;
                List<WebElement> lis = issuesList.findElements(By.tagName("li"));
                int size = lis.size();
                issueCounter += size;
            }
        }
        System.out.println("Issues " + issueCounter);
        return issueCounter;
    }

}