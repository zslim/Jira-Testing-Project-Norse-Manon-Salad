package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @FindBy(id = "project-name-val")
    WebElement projectName;


    Login login;

    public BrowseIssues(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
        login = new Login(driver);
    }

    public void displayAllIssues(){
        waitUntilElementLoaded(headerIssuesField);
        headerIssuesField.click();
        waitUntilElementLoaded(searchForIssues);
        searchForIssues.click();
        waitUntilElementLoaded(searchTitle);
    }


    public int getNumOfIssues(){
        String text = numberOfIssues.getText();
        String[] split = text.split(" ");
        int max = Integer.parseInt(split[2]);
        return max;
    }

    public Map getNumOfIssuesPerProject(String[] projects){

        Map<String, Integer > issuesPerProject = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        for (int i = 0; i < projects.length; i++) {
            displayAllIssues();
            waitUntilElementClickable(projectFilter);
            projectFilter.click();
            waitUntilElementClickable(projectFilterInputField);
            projectFilterInputField.sendKeys(projects[i] + Keys.ENTER + Keys.ESCAPE);
            wait.until(ExpectedConditions.textToBePresentInElement(filteredId, projects[i]));
            issuesPerProject.put(projects[i], getNumOfIssues());
        }

        return issuesPerProject;
    }


    String validateIssuesDisplayed(){
        return searchTitle.getText();
    }
}
