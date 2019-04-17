package com.codecool.testautomation.norsemanonsalad.features;

import com.codecool.testautomation.norsemanonsalad.testutils.Utils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import java.net.MalformedURLException;

class BrowseProjectTest {

    private Login login;
    private BrowseProject browseProject;
    private static final String PROJECTS_DATA = "/browse_project_projects.csv";
    private static final String CATEGORY_DATA = "/browse_project_categories.csv";
    private static final String TYPE_DATA = "/browse_project_types.csv";

    @BeforeAll
    static void init() {
        Utils.setDriverPath();
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        login = new Login(Utils.createDriver());
        browseProject = new BrowseProject(login.getDriver());
        login.loginSuccessful();
    }

    @AfterEach
    void tearDown() {
        browseProject.driver.quit();
    }

    @Test
    void navigateToProjectsDirectly() {
        browseProject.navigateToProjectsDirectly();
        boolean isProjectHeaderPresent = browseProject.validateNavigateToProjects();
        assertTrue(isProjectHeaderPresent);
    }

    @Test
    void navigateToProjectsUsingMenu() {
        browseProject.navigateToProjectsUsingMenu();
        boolean isProjectHeaderPresent = browseProject.validateNavigateToProjects();
        assertTrue(isProjectHeaderPresent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PROJECTS_DATA, numLinesToSkip = 1)
    void checkProjectIsPresent(String projectName, String exceptedTitle) {
        browseProject.navigateToProjectsDirectly();
        browseProject.searchForProject(projectName);
        assertTrue(browseProject.validateTestProjectsPresent(exceptedTitle));
    }

    @ParameterizedTest
    @CsvFileSource(resources = CATEGORY_DATA, numLinesToSkip = 1)
    void categoryFilter(String category, int numOfProjects) {
        browseProject.navigateToProjectsDirectly();
        browseProject.chooseCategory(category);
        assertEquals(numOfProjects, browseProject.validateFilter());
    }

    @ParameterizedTest
    @CsvFileSource(resources = TYPE_DATA, numLinesToSkip = 1)
    void typeFilter(String type, int numOfProjects) {
        browseProject.navigateToProjectsDirectly();
        browseProject.chooseType(type);
        assertEquals(numOfProjects, browseProject.validateFilter());
    }
}