package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectVersions extends Feature {

    @FindBy(id = "releases-add__version")
    WebElement newReleaseForm;

    @FindBy(name = "name")
    WebElement nameField;

    @FindBy(name = "startDate")
    WebElement startDateField;

    @FindBy(name = "releaseDate")
    WebElement releaseDateField;

    @FindBy(name = "description")
    WebElement descriptionField;

    @FindBy(xpath = "//table[@id='versions-table']//td[@class='versions-table__name'][1]//a")
    WebElement nameOfVersionOnTop;

    @FindBy(css = "span.aui-iconfont-more")
    WebElement actionButtonOfSingleVersion;

    @FindBy(css = "a.project-config-operations-delete")
    WebElement deleteOption;

    @FindBy(id = "submit")
    WebElement confirmDeleteButton;

    @FindBy(css = "span.aui-iconfont-approve")
    WebElement approveSign;

    private static final String RELEASE_PAGE_URL = "https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin%3Arelease-page";

    public ProjectVersions(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToReleases() {
        driver.get(RELEASE_PAGE_URL);
        waitUntilElementLoaded(newReleaseForm);
    }

    public void addNewVersion(String versionName, String startDate, String releaseDate, String description) {
        nameField.sendKeys(Utils.nullToEmptyString(versionName));
        startDateField.sendKeys(Utils.nullToEmptyString(startDate));
        releaseDateField.sendKeys(Utils.nullToEmptyString(releaseDate));
        descriptionField.sendKeys(Utils.nullToEmptyString(description));
        nameField.submit();
    }

    public void deleteVersion(String versionName) {
        String versionFilterUrl = "https://jira.codecool.codecanvas.hu/projects/PP1?selectedItem=com.atlassian.jira.jira-projects-plugin:release-page&status=unreleased&contains=" + versionName;
        driver.get(versionFilterUrl);
        waitUntilElementLoaded(newReleaseForm);

        actionButtonOfSingleVersion.click();
        waitUntilElementLoaded(deleteOption);
        deleteOption.click();
        waitUntilElementLoaded(confirmDeleteButton);
        confirmDeleteButton.click();
    }

    public String validateNewVersion() {
        waitUntilElementLoaded(approveSign);
        return nameOfVersionOnTop.getText();
    }

}
