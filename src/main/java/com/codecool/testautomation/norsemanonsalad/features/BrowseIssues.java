package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    @FindBy(xpath = "(//SPAN[text()='1 of 233'])[1]")
    WebElement numberOfIssues;


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


    String validateIssuesDisplayed(){
        return searchTitle.getText();
    }
}
