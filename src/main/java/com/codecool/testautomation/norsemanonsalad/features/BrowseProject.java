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

    @FindBy(xpath = "//*[@id=\"filter-projects\"]/div/h2")
    WebElement categoryName;

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
                return isElementAttributeContains(toucanProject, attribute, expectedTitle);
            case "JETI Project":
                return isElementAttributeContains(jetiProject, attribute, expectedTitle);
            case "COALA Project":
                return isElementAttributeContains(coalaProject, attribute, expectedTitle);
            default:
                return false;
        }
    }

    void chooseOneCategory(String category) {
        switch (category){
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

    boolean validateCategoryFilter(int numOfProjects) {
        List<WebElement> elementsList = driver.findElements(By.xpath("//*[@id=\"projects\"]/div/table/tbody/tr"));
        int projectsShown = elementsList.size();
        boolean isEqual = projectsShown == numOfProjects;
        //elementsList.clear();
        return isEqual;
    }


}
