package com.codecool.testautomation.norsemanonsalad.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectVersions extends Feature {

    @FindBy(id = "releases-add__version")
    WebElement newReleaseForm;

    @FindBy(name = "name")
    WebElement nameField;

    @FindBy(name = "startDate")
    WebElement startDateField;

    @FindBy(name = "releaseDate")
    WebElement releaseDateField;

    @FindBy(name = "dascription")
    WebElement descriptionField;

    @FindBy(xpath = "//table[@id='versions-table']/tbody[contains(concat=(' ', @class, ' '), ' items ')]/tr[1]/td[@class='versions-table__name']/a")
    WebElement nameOfVersionOnTop;

    private static final String RELEASE_PAGE_URL = "https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin%3Arelease-page";

    public ProjectVersions(WebDriver driver) {
        super(driver);
    }

    public void navigateToReleases() {
        driver.get(RELEASE_PAGE_URL);
        waitUntilElementLoaded(newReleaseForm);
    }

    public void addNewVersion(String versionName, String startDate, String releaseDate, String description) {
        nameField.sendKeys(versionName);
        startDateField.sendKeys(startDate);
        releaseDateField.sendKeys(releaseDate);
        descriptionField.sendKeys(description);
        nameField.submit();
    }

    public String validateNewVersion() {
        return nameOfVersionOnTop.getText();
    }

}
