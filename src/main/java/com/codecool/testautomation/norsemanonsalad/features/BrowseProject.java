package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BrowseProject extends Feature {

    @FindBy(id = "browse_link")
    WebElement projectDropdownMenu;

    @FindBy(id = "project_view_all_link_lnk")
    WebElement allProjectsOption;

    @FindBy(xpath = "//*[@id=\"content\"]/header/div//h1")
    WebElement browseProjectHeader;

    @FindBy(xpath = "//*[@id=\"projects\"]/div/table/tbody//a[contains(text(),\"COALA\")]")
    WebElement coalaProject;

    @FindBy(partialLinkText = "JETI")
    WebElement jetiProject;

    @FindBy(partialLinkText = "TOUCAN")
    WebElement toucanProject;

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

    boolean validateNavigateToProjects(){
        return isElementPresent(browseProjectHeader);
    }

    boolean validateStaticProjectsPresent(String projectTitle) {
        String attribute = "original-title";
        switch (projectTitle) {
            case "TOUCAN projekt":
                return isElementAttributeContains(toucanProject, attribute, projectTitle);
            case "JETI project":
                return isElementAttributeContains(jetiProject, attribute, projectTitle);
            case "COALA project":
                return isElementAttributeContains(coalaProject, attribute, projectTitle);
            default:
                return false;
        }
    }
}
