package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class BrowseProject extends Feature {

    @FindBy(id = "browse_link")
    WebElement projectDropdownMenu;

    @FindBy(id = "project_view_all_link_lnk")
    WebElement allProjectsOption;

    @FindBy(xpath = "//*[@id=\"content\"]/header/div//h1")
    WebElement browseProjectHeader;

    @FindBy(id = "project-filter-text")
    WebElement search;

    @FindBy(partialLinkText = "COALA")
    WebElement coalaProject;

    @FindBy(partialLinkText = "JETI")
    WebElement jetiProject;

    @FindBy(partialLinkText = "TOUCAN")
    WebElement toucanProject;

    @FindBy(id = "all-panel-tab-lnk")
    WebElement allProjects;

    @FindBy(id = "10000-panel-tab-lnk")
    WebElement staticProjects;

    @FindBy(id = "none-panel-tab-lnk")
    WebElement projectsWithNoCategory;

    @FindBy(id = "recent-panel-tab-lnk")
    WebElement recentProjects;

    @FindBy(id = "all-project-type")
    WebElement allProjectType;

    @FindBy(id = "software-project-type")
    WebElement softwareProjectType;

    @FindBy(id = "business-project-type")
    WebElement businessProjectType;

    private Login login;

    protected BrowseProject(WebDriver driver) {
        super(driver);
        this.login = new Login(driver);
        PageFactory.initElements(driver, this);
    }

    void navigateToProjectsDirectly() {
        login.loginSuccessful();
        waitUntilElementLoaded(projectDropdownMenu); // Waiting for the dropdown makes sure that login is finished by
        // the time we navigate to the project listing page
        driver.get("https://jira.codecool.codecanvas.hu/secure/BrowseProjects.jspa");
    }

    void navigateToProjectsUsingMenu() {
        login.loginSuccessful();
        waitUntilElementLoaded(projectDropdownMenu);
        projectDropdownMenu.click();
        waitUntilElementLoaded(allProjectsOption);
        allProjectsOption.click();
        waitUntilElementLoaded(browseProjectHeader);
    }

    boolean validateNavigateToProjects() {
        return isElementPresent(browseProjectHeader);
    }

    void searchProject(String projectName) {
        search.sendKeys(projectName);
    }

    void emptySearchField() {
        search.clear();
    }

    boolean validateTestProjectsPresent(String expectedTitle) {
        String attribute = "title";
        switch (expectedTitle) {
            case "TOUCAN projekt":
                return doesContainAttribute(toucanProject, attribute, expectedTitle);
            case "JETI Project":
                return doesContainAttribute(jetiProject, attribute, expectedTitle);
            case "COALA Project":
                return doesContainAttribute(coalaProject, attribute, expectedTitle);
            default:
                return false;
        }
    }

    void chooseOneCategory(String category) {
        switch (category) {
            case "Static":
                staticProjects.click();
                break;
            case "No category":
                projectsWithNoCategory.click();
                break;
            case "All categories":
                allProjects.click();
                break;
            case "Recent projects":
                recentProjects.click();
                break;
        }
    }

    void chooseOneType(String type) {
        switch (type) {
            case "All project types":
                allProjectType.click();
                break;
            case "Software project types":
                softwareProjectType.click();
                break;
            case "Business project types":
                businessProjectType.click();
                break;
        }
    }

    boolean validateFilter(int numOfProjects) {
        String rowXpath = "//*[@id=\"projects\"]/div/table/tbody/tr";
        List<WebElement> elementsList = driver.findElements(By.xpath(rowXpath));
        int projectsShown = elementsList.size();
        return projectsShown == numOfProjects;
    }


}
