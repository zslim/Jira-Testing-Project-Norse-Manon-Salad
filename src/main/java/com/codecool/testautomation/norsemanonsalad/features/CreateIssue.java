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

    @FindBy(id = "all-projects")
    WebElement allProjectsList;

    @FindBy(xpath = "//div[@id='issuetype-suggestions']/div/ul")
    WebElement issueTypesList;

    public CreateIssue(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
