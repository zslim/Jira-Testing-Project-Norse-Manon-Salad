package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BrowseProject extends Feature {

    private static final String PROJECTS_PAGE_URL = "https://jira.codecool.codecanvas.hu/secure/BrowseProjects.jspa";

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

    public BrowseProject(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    void navigateToProjectsDirectly() {
        driver.get(PROJECTS_PAGE_URL);
    }

    void navigateToProjectsUsingMenu() {
        projectDropdownMenu.click();
        waitUntilElementLoaded(allProjectsOption);
        allProjectsOption.click();
        waitUntilElementLoaded(browseProjectHeader);
    }

    boolean validateNavigateToProjects() {
        return isElementPresent(browseProjectHeader);
    }

    void searchForProject(String projectName) {
        emptySearchField();
        search.sendKeys(projectName);
    }

    private void emptySearchField() {
        waitUntilElementLoaded(search);
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

    void clickCategory(String category) throws BadTestDataException {
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
            default:
                throw new BadTestDataException("Category " + category + " couldn't be clicked");
        }
    }

    public void chooseCategory(String category) {
        waitUntilElementLoaded(staticProjects);
        try {
            clickCategory(category);
        } catch (BadTestDataException e) {
            e.printStackTrace();
        }
    }

    private void clickType(String type) throws BadTestDataException {
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
            default:
                throw new BadTestDataException("Type " + type + " couldn't be clicked");
        }
    }

    void chooseType(String type) {
        waitUntilElementLoaded(allProjectType);
        try {
            clickType(type);
        } catch (BadTestDataException e) {
            e.printStackTrace();
        }
    }

    int validateFilter() {
        String rowXpath = "//*[@id=\"projects\"]/div/table/tbody/tr";
        List<WebElement> elementsList = driver.findElements(By.xpath(rowXpath));
        int projectsShown = elementsList.size();
        return projectsShown;
    }


}
